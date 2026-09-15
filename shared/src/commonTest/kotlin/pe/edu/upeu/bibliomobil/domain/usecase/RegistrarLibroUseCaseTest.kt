package pe.edu.upeu.bibliomobil.domain.usecase

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.bibliomobil.fakes.FakeLibroRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RegistrarLibroUseCaseTest {
    private val repository = FakeLibroRepository()
    private val useCase = RegistrarLibroUseCase(repository)

    @Test fun aceptaLibroValido() = runTest {
        val resultado = useCase(" Rayuela ", " Julio Cortázar ", "1963", "4")
        assertTrue(resultado.isSuccess)
        assertEquals("Rayuela", resultado.getOrThrow().titulo)
        assertEquals("Julio Cortázar", resultado.getOrThrow().autor)
    }

    @Test fun informaTituloObligatorio() = runTest {
        assertEquals(RegistrarLibroUseCase.TITULO_OBLIGATORIO, errorDe(titulo = "").titulo)
    }

    @Test fun informaAutorObligatorio() = runTest {
        assertEquals(RegistrarLibroUseCase.AUTOR_OBLIGATORIO, errorDe(autor = " ").autor)
    }

    @Test fun informaAnioObligatorio() = runTest {
        assertEquals(RegistrarLibroUseCase.ANIO_OBLIGATORIO, errorDe(anio = "").anio)
    }

    @Test fun informaAnioNoEntero() = runTest {
        assertEquals(RegistrarLibroUseCase.ANIO_ENTERO, errorDe(anio = "mil novecientos").anio)
    }

    @Test fun informaAnioFueraDeRango() = runTest {
        assertEquals(RegistrarLibroUseCase.ANIO_RANGO, errorDe(anio = "1400").anio)
    }

    @Test fun informaEjemplaresObligatorios() = runTest {
        assertEquals(
            RegistrarLibroUseCase.EJEMPLARES_OBLIGATORIOS,
            errorDe(ejemplares = "").ejemplares
        )
    }

    @Test fun informaEjemplaresNoEnteros() = runTest {
        assertEquals(
            RegistrarLibroUseCase.EJEMPLARES_ENTEROS,
            errorDe(ejemplares = "tres").ejemplares
        )
    }

    @Test fun informaEjemplaresNegativos() = runTest {
        assertEquals(
            RegistrarLibroUseCase.EJEMPLARES_NO_NEGATIVOS,
            errorDe(ejemplares = "-1").ejemplares
        )
    }

    @Test fun elRepositorioAsignaElId() = runTest {
        assertEquals(1L, useCase("1984", "George Orwell", "1949", "2").getOrThrow().id)
    }

    @Test fun propagaElFalloDelRepositorioComoResultFailure() = runTest {
        val fallo = IllegalStateException("Repositorio no disponible")
        repository.falloAlRegistrar = fallo
        val resultado = useCase("1984", "George Orwell", "1949", "2")
        assertTrue(resultado.isFailure)
        assertIs<IllegalStateException>(resultado.exceptionOrNull())
        assertEquals(fallo, resultado.exceptionOrNull())
    }

    private suspend fun errorDe(
        titulo: String = "1984",
        autor: String = "George Orwell",
        anio: String = "1949",
        ejemplares: String = "2"
    ): ErroresDeLibro {
        val error = useCase(titulo, autor, anio, ejemplares).exceptionOrNull()
        return assertIs<LibroInvalidoException>(error).errores
    }
}
