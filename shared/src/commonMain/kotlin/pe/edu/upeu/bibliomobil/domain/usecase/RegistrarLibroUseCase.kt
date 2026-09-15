package pe.edu.upeu.bibliomobil.domain.usecase

import pe.edu.upeu.bibliomobil.domain.model.Libro
import pe.edu.upeu.bibliomobil.domain.repository.LibroRepository

data class ErroresDeLibro(
    val titulo: String? = null,
    val autor: String? = null,
    val anio: String? = null,
    val ejemplares: String? = null
) {
    val tieneErrores: Boolean
        get() = titulo != null || autor != null || anio != null || ejemplares != null
}

class LibroInvalidoException(val errores: ErroresDeLibro) :
    IllegalArgumentException("Los datos del libro son inválidos")

class RegistrarLibroUseCase(
    private val repository: LibroRepository
) {
    suspend operator fun invoke(
        titulo: String,
        autor: String,
        anio: String,
        ejemplares: String
    ): Result<Libro> = resultadoDe {
        val anioNumero = anio.toIntOrNull()
        val ejemplaresNumero = ejemplares.toIntOrNull()
        val errores = ErroresDeLibro(
            titulo = if (titulo.isBlank()) TITULO_OBLIGATORIO else null,
            autor = if (autor.isBlank()) AUTOR_OBLIGATORIO else null,
            anio = when {
                anio.isBlank() -> ANIO_OBLIGATORIO
                anioNumero == null -> ANIO_ENTERO
                anioNumero !in Libro.ANIO_MINIMO..Libro.ANIO_MAXIMO -> ANIO_RANGO
                else -> null
            },
            ejemplares = when {
                ejemplares.isBlank() -> EJEMPLARES_OBLIGATORIOS
                ejemplaresNumero == null -> EJEMPLARES_ENTEROS
                ejemplaresNumero < 0 -> EJEMPLARES_NO_NEGATIVOS
                else -> null
            }
        )
        if (errores.tieneErrores) throw LibroInvalidoException(errores)

        repository.registrar(
            Libro(
                id = 0L,
                titulo = titulo.trim(),
                autor = autor.trim(),
                anio = requireNotNull(anioNumero),
                ejemplares = requireNotNull(ejemplaresNumero)
            )
        )
    }

    companion object {
        const val TITULO_OBLIGATORIO = "El título es obligatorio"
        const val AUTOR_OBLIGATORIO = "El autor es obligatorio"
        const val ANIO_OBLIGATORIO = "El año es obligatorio"
        const val ANIO_ENTERO = "El año debe ser un número entero"
        const val ANIO_RANGO = "El año debe estar entre 1450 y 2026"
        const val EJEMPLARES_OBLIGATORIOS = "Los ejemplares son obligatorios"
        const val EJEMPLARES_ENTEROS = "Los ejemplares deben ser un número entero"
        const val EJEMPLARES_NO_NEGATIVOS = "Los ejemplares no pueden ser negativos"
    }
}
