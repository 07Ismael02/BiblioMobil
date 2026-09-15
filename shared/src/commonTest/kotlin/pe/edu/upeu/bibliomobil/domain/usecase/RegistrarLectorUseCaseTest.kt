package pe.edu.upeu.bibliomobil.domain.usecase

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.bibliomobil.fakes.FakeLectorRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RegistrarLectorUseCaseTest {
    @Test fun rechazaCorreoInvalido() = runTest {
        val resultado = RegistrarLectorUseCase(FakeLectorRepository())(
            "Ana", "correo-invalido", "987654321"
        )
        val error = assertIs<LectorInvalidoException>(resultado.exceptionOrNull())
        assertEquals(RegistrarLectorUseCase.CORREO_INVALIDO, error.errores.correo)
    }

    @Test fun rechazaTelefonoCorto() = runTest {
        val resultado = RegistrarLectorUseCase(FakeLectorRepository())(
            "Ana", "ana@example.com", "12345"
        )
        val error = assertIs<LectorInvalidoException>(resultado.exceptionOrNull())
        assertEquals(RegistrarLectorUseCase.TELEFONO_INVALIDO, error.errores.telefono)
    }

    @Test fun guardaTelefonoEnBlancoComoNull() = runTest {
        val resultado = RegistrarLectorUseCase(FakeLectorRepository())(
            "Ana", "ana@example.com", "   "
        )
        assertEquals(null, resultado.getOrThrow().telefono)
    }
}
