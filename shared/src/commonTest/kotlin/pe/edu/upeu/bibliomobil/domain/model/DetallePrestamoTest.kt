package pe.edu.upeu.bibliomobil.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DetallePrestamoTest {
    private val libro = Libro(1L, "Cien años de soledad", "Gabriel García Márquez", 1967, 4)

    @Test fun rechazaCeroDias() {
        assertFailsWith<IllegalArgumentException> { DetallePrestamo(libro, 0) }
    }

    @Test fun rechazaDieciseisDias() {
        assertFailsWith<IllegalArgumentException> { DetallePrestamo(libro, 16) }
    }

    @Test fun calculaMultaPorCuatroDiasDeRetraso() {
        assertEquals(6.0, DetallePrestamo(libro, 5).multaPorRetraso(4))
    }
}
