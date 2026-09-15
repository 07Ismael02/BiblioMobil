package pe.edu.upeu.bibliomobil.domain.usecase

import pe.edu.upeu.bibliomobil.domain.model.Lector
import pe.edu.upeu.bibliomobil.domain.repository.LectorRepository

data class ErroresDeLector(
    val nombre: String? = null,
    val correo: String? = null,
    val telefono: String? = null
) {
    val tieneErrores: Boolean
        get() = nombre != null || correo != null || telefono != null
}

class LectorInvalidoException(val errores: ErroresDeLector) :
    IllegalArgumentException("Los datos del lector son inválidos")

class RegistrarLectorUseCase(
    private val repository: LectorRepository
) {
    suspend operator fun invoke(
        nombre: String,
        correo: String,
        telefono: String
    ): Result<Lector> = resultadoDe {
        val nombreLimpio = nombre.trim()
        val correoLimpio = correo.trim()
        val telefonoLimpio = telefono.trim()
        val errores = ErroresDeLector(
            nombre = if (nombreLimpio.isEmpty()) NOMBRE_OBLIGATORIO else null,
            correo = when {
                correoLimpio.isEmpty() -> CORREO_OBLIGATORIO
                !EMAIL_REGEX.matches(correoLimpio) -> CORREO_INVALIDO
                else -> null
            },
            telefono = if (
                telefonoLimpio.isNotEmpty() && !TELEFONO_REGEX.matches(telefonoLimpio)
            ) TELEFONO_INVALIDO else null
        )
        if (errores.tieneErrores) throw LectorInvalidoException(errores)

        repository.registrar(
            Lector(
                id = 0L,
                nombre = nombreLimpio,
                correo = correoLimpio,
                telefono = telefonoLimpio.ifBlank { null }
            )
        )
    }

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        private val TELEFONO_REGEX = Regex("^[0-9]{6,9}$")

        const val NOMBRE_OBLIGATORIO = "El nombre es obligatorio"
        const val CORREO_OBLIGATORIO = "El correo es obligatorio"
        const val CORREO_INVALIDO = "El correo no tiene un formato válido"
        const val TELEFONO_INVALIDO = "El teléfono debe tener entre 6 y 9 dígitos"
    }
}
