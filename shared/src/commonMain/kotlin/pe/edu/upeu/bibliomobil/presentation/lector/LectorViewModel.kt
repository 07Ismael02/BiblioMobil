package pe.edu.upeu.bibliomobil.presentation.lector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.edu.upeu.bibliomobil.domain.usecase.LectorInvalidoException
import pe.edu.upeu.bibliomobil.domain.usecase.ListarLectoresUseCase
import pe.edu.upeu.bibliomobil.domain.usecase.RegistrarLectorUseCase

class LectorViewModel(
    private val registrarLector: RegistrarLectorUseCase,
    private val listarLectores: ListarLectoresUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LectorUiState())
    val uiState: StateFlow<LectorUiState> = _uiState.asStateFlow()

    init {
        cargarLectores()
    }

    fun cargarLectores() {
        _uiState.update { it.copy(fase = FaseLectores.Cargando) }
        viewModelScope.launch {
            listarLectores().fold(
                onSuccess = { lectores ->
                    _uiState.update {
                        it.copy(
                            fase = if (lectores.isEmpty()) {
                                FaseLectores.SinLectores
                            } else {
                                FaseLectores.ConLectores(lectores.map { lector -> lector.aUi() })
                            }
                        )
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(fase = FaseLectores.Error(ERROR_CARGA)) }
                }
            )
        }
    }

    fun onNombreChange(valor: String) = actualizarFormulario {
        copy(nombre = valor, errorNombre = null)
    }

    fun onCorreoChange(valor: String) = actualizarFormulario {
        copy(correo = valor, errorCorreo = null)
    }

    fun onTelefonoChange(valor: String) = actualizarFormulario {
        copy(telefono = valor, errorTelefono = null)
    }

    fun registrar() {
        if (_uiState.value.registrando) return
        val formulario = _uiState.value.formulario
        _uiState.update { it.copy(registrando = true, mensajeExito = null) }

        viewModelScope.launch {
            registrarLector(
                nombre = formulario.nombre,
                correo = formulario.correo,
                telefono = formulario.telefono
            ).fold(
                onSuccess = { lector ->
                    _uiState.update {
                        it.copy(
                            formulario = FormularioLector(),
                            registrando = false,
                            mensajeExito = "Lector \"${lector.nombre}\" registrado correctamente"
                        )
                    }
                    cargarLectores()
                },
                onFailure = { error ->
                    if (error is LectorInvalidoException) {
                        _uiState.update {
                            it.copy(
                                formulario = formulario.copy(
                                    errorNombre = error.errores.nombre,
                                    errorCorreo = error.errores.correo,
                                    errorTelefono = error.errores.telefono
                                ),
                                registrando = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                fase = FaseLectores.Error(ERROR_CARGA),
                                registrando = false
                            )
                        }
                    }
                }
            )
        }
    }

    private fun actualizarFormulario(transformar: FormularioLector.() -> FormularioLector) {
        _uiState.update { it.copy(formulario = it.formulario.transformar()) }
    }

    companion object {
        const val ERROR_CARGA = "No se pudo cargar la cartera de lectores"
    }
}
