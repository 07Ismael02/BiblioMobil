package pe.edu.upeu.bibliomobil.domain.repository

import pe.edu.upeu.bibliomobil.domain.model.Libro

/** Gestiona el registro y la consulta del catálogo de libros de la biblioteca. */
interface LibroRepository {
    /** Registra un libro en el catálogo y devuelve el libro con su identificador asignado. */
    suspend fun registrar(libro: Libro): Libro

    /** Devuelve los libros del catálogo en su orden de registro. */
    suspend fun listar(): List<Libro>
}
