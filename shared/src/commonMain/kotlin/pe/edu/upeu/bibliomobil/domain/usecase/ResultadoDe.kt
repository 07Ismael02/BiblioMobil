package pe.edu.upeu.bibliomobil.domain.usecase

import kotlinx.coroutines.CancellationException

suspend inline fun <T> resultadoDe(crossinline accion: suspend () -> T): Result<T> =
    try {
        Result.success(accion())
    } catch (error: CancellationException) {
        throw error
    } catch (error: Throwable) {
        Result.failure(error)
    }
