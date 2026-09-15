package pe.edu.upeu.bibliomobil.fakes

import pe.edu.upeu.bibliomobil.domain.model.Libro
import pe.edu.upeu.bibliomobil.domain.repository.LibroRepository

class FakeLibroRepository(librosIniciales: List<Libro> = emptyList()) : LibroRepository {
    private val libros = librosIniciales.toMutableList()
    private var siguienteId = (libros.maxOfOrNull { it.id } ?: 0L) + 1L
    var falloAlRegistrar: Throwable? = null
    var falloAlListar: Throwable? = null

    override suspend fun registrar(libro: Libro): Libro {
        falloAlRegistrar?.let { throw it }
        return libro.copy(id = siguienteId++).also(libros::add)
    }

    override suspend fun listar(): List<Libro> {
        falloAlListar?.let { throw it }
        return libros.toList()
    }
}
