package pe.edu.upeu.bibliomobil.domain.repository

import pe.edu.upeu.bibliomobil.domain.model.Lector

/** Gestiona el registro y la consulta de los lectores de la biblioteca. */
interface LectorRepository {
    /** Registra un lector y devuelve el registro con su identificador asignado. */
    suspend fun registrar(lector: Lector): Lector

    /** Devuelve los lectores de la biblioteca en su orden de registro. */
    suspend fun listar(): List<Lector>
}
