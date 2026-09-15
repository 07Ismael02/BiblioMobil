package pe.edu.upeu.bibliomobil.presentation.libro

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import pe.edu.upeu.bibliomobil.domain.model.Libro
import pe.edu.upeu.bibliomobil.domain.usecase.ListarLibrosUseCase
import pe.edu.upeu.bibliomobil.domain.usecase.RegistrarLibroUseCase
import pe.edu.upeu.bibliomobil.fakes.FakeLibroRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class LibroViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeTest fun instalarMain() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest fun restaurarMain() {
        Dispatchers.resetMain()
    }

    @Test fun arrancaEnSinLibros() = runTest {
        assertEquals(FaseLibros.SinLibros, viewModel(FakeLibroRepository()).uiState.value.fase)
    }

    @Test fun muestraLineaSecundariaExacta() = runTest {
        val repository = FakeLibroRepository(
            listOf(Libro(1L, "El túnel", "Ernesto Sábato", 1998, 3))
        )
        val fase = assertIs<FaseLibros.ConLibros>(viewModel(repository).uiState.value.fase)
        assertEquals("1998 · 3 ejemplares", fase.libros.single().lineaSecundaria)
    }

    @Test fun pasaAErrorSiElRepositorioFalla() = runTest {
        val repository = FakeLibroRepository().apply {
            falloAlListar = IllegalStateException("sin conexión")
        }
        val fase = assertIs<FaseLibros.Error>(viewModel(repository).uiState.value.fase)
        assertEquals(LibroViewModel.ERROR_CARGA, fase.mensaje)
    }

    @Test fun dejaErroresDeValidacionEnFormularioSinCambiarLaFase() = runTest {
        val viewModel = viewModel(FakeLibroRepository())
        viewModel.registrar()
        assertEquals(FaseLibros.SinLibros, viewModel.uiState.value.fase)
        assertEquals(
            RegistrarLibroUseCase.TITULO_OBLIGATORIO,
            viewModel.uiState.value.formulario.errorTitulo
        )
        assertEquals(
            RegistrarLibroUseCase.AUTOR_OBLIGATORIO,
            viewModel.uiState.value.formulario.errorAutor
        )
    }

    @Test fun registrarLimpiaFormularioYRecarga() = runTest {
        val viewModel = viewModel(FakeLibroRepository())
        viewModel.onTituloChange("El túnel")
        viewModel.onAutorChange("Ernesto Sábato")
        viewModel.onAnioChange("1948")
        viewModel.onEjemplaresChange("3")
        viewModel.registrar()
        assertEquals(FormularioLibro(), viewModel.uiState.value.formulario)
        assertEquals(
            "Libro \"El túnel\" registrado correctamente",
            viewModel.uiState.value.mensajeExito
        )
        assertIs<FaseLibros.ConLibros>(viewModel.uiState.value.fase)
    }

    private fun viewModel(repository: FakeLibroRepository) = LibroViewModel(
        registrarLibro = RegistrarLibroUseCase(repository),
        listarLibros = ListarLibrosUseCase(repository)
    )
}
