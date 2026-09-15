package pe.edu.upeu.bibliomobil.presentation.libro

import pe.edu.upeu.bibliomobil.domain.model.Libro

sealed interface FaseLibros {
    data object Cargando : FaseLibros
    data object SinLibros : FaseLibros
    data class ConLibros(val libros: List<LibroUi>) : FaseLibros
    data class Error(val mensaje: String) : FaseLibros
}

data class FormularioLibro(
    val titulo: String = "",
    val autor: String = "",
    val anio: String = "",
    val ejemplares: String = "",
    val errorTitulo: String? = null,
    val errorAutor: String? = null,
    val errorAnio: String? = null,
    val errorEjemplares: String? = null
)

data class LibroUi(
    val id: Long,
    val titulo: String,
    val autor: String,
    val lineaSecundaria: String,
    val requiereReposicion: Boolean
)

fun Libro.aUi(): LibroUi = LibroUi(
    id = id,
    titulo = titulo,
    autor = autor,
    lineaSecundaria = "$anio · $ejemplares ${if (ejemplares == 1) "ejemplar" else "ejemplares"}",
    requiereReposicion = requiereReposicion
)

data class LibroUiState(
    val fase: FaseLibros = FaseLibros.Cargando,
    val formulario: FormularioLibro = FormularioLibro(),
    val registrando: Boolean = false,
    val mensajeExito: String? = null
)
