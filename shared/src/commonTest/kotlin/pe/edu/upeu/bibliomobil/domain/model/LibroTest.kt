package pe.edu.upeu.bibliomobil.domain.model

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LibroTest {
    @Test fun rechazaTituloVacio() {
        assertFailsWith<IllegalArgumentException> { libro(titulo = "   ") }
    }

    @Test fun rechazaAnioFueraDeRango() {
        assertFailsWith<IllegalArgumentException> { libro(anio = Libro.ANIO_MINIMO - 1) }
    }

    @Test fun requiereReposicionConDosEjemplares() {
        assertTrue(libro(ejemplares = 2).requiereReposicion)
    }

    @Test fun noRequiereReposicionConTresEjemplares() {
        assertFalse(libro(ejemplares = 3).requiereReposicion)
    }

    private fun libro(
        titulo: String = "El Principito",
        anio: Int = 1943,
        ejemplares: Int = 3
    ) = Libro(1L, titulo, "Antoine de Saint-Exupéry", anio, ejemplares)
}
