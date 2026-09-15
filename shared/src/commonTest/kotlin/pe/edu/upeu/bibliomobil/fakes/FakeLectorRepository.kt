package pe.edu.upeu.bibliomobil.fakes

import pe.edu.upeu.bibliomobil.domain.model.Lector
import pe.edu.upeu.bibliomobil.domain.repository.LectorRepository

class FakeLectorRepository(lectoresIniciales: List<Lector> = emptyList()) : LectorRepository {
    private val lectores = lectoresIniciales.toMutableList()
    private var siguienteId = (lectores.maxOfOrNull { it.id } ?: 0L) + 1L
    var falloAlRegistrar: Throwable? = null
    var falloAlListar: Throwable? = null

    override suspend fun registrar(lector: Lector): Lector {
        falloAlRegistrar?.let { throw it }
        return lector.copy(id = siguienteId++).also(lectores::add)
    }

    override suspend fun listar(): List<Lector> {
        falloAlListar?.let { throw it }
        return lectores.toList()
    }
}
