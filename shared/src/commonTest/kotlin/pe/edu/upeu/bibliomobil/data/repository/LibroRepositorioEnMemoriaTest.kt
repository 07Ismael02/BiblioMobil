package pe.edu.upeu.bibliomobil.data.repository

import kotlinx.coroutines.test.runTest
import pe.edu.upeu.bibliomobil.domain.model.Libro
import kotlin.test.Test
import kotlin.test.assertEquals

class LibroRepositorioEnMemoriaTest {
    @Test fun asignaIdsCorrelativos() = runTest {
        val repository = LibroRepositorioEnMemoria()
        val primero = repository.registrar(libro("Primero"))
        val segundo = repository.registrar(libro("Segundo"))
        assertEquals(1L, primero.id)
        assertEquals(2L, segundo.id)
    }

    @Test fun listaEnOrdenDeRegistro() = runTest {
        val repository = LibroRepositorioEnMemoria()
        repository.registrar(libro("Primero"))
        repository.registrar(libro("Segundo"))
        assertEquals(listOf("Primero", "Segundo"), repository.listar().map { it.titulo })
    }

    private fun libro(titulo: String) = Libro(0L, titulo, "Autor", 2000, 3)
}
