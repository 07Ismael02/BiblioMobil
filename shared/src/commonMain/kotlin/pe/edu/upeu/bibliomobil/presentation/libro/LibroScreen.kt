package pe.edu.upeu.bibliomobil.presentation.libro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pe.edu.upeu.bibliomobil.presentation.components.EstadoVacio
import pe.edu.upeu.bibliomobil.presentation.components.MensajeExito
import pe.edu.upeu.bibliomobil.presentation.components.ValidatedTextField

@Composable
fun LibroScreen(viewModel: LibroViewModel, modifier: Modifier = Modifier) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Catálogo de libros", style = MaterialTheme.typography.headlineSmall)
        FormularioLibroCard(
            formulario = estado.formulario,
            registrando = estado.registrando,
            onTituloChange = viewModel::onTituloChange,
            onAutorChange = viewModel::onAutorChange,
            onAnioChange = viewModel::onAnioChange,
            onEjemplaresChange = viewModel::onEjemplaresChange,
            onRegistrar = viewModel::registrar
        )
        estado.mensajeExito?.let {
            MensajeExito(it)
        }

        when (val fase = estado.fase) {
            FaseLibros.Cargando -> Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Cargando catálogo…", modifier = Modifier.padding(top = 8.dp))
            }

            FaseLibros.SinLibros -> EstadoVacio(
                icono = Icons.AutoMirrored.Filled.MenuBook,
                titulo = "Sin libros",
                descripcion = "Aún no hay libros registrados en el catálogo.",
                modifier = Modifier.fillMaxWidth().weight(1f)
            )

            is FaseLibros.ConLibros -> {
                Text(
                    if (fase.libros.size == 1) "1 libro" else "${fase.libros.size} libros",
                    style = MaterialTheme.typography.titleMedium
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(fase.libros, key = { it.id }) { libro -> LibroCard(libro) }
                }
            }

            is FaseLibros.Error -> EstadoVacio(
                icono = Icons.AutoMirrored.Filled.MenuBook,
                titulo = fase.mensaje,
                descripcion = "Intenta cargar el catálogo nuevamente.",
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = MaterialTheme.colorScheme.error,
                accion = {
                    Button(onClick = viewModel::cargarLibros) {
                        Text("Reintentar")
                    }
                }
            )
        }
    }
}

@Composable
private fun FormularioLibroCard(
    formulario: FormularioLibro,
    registrando: Boolean,
    onTituloChange: (String) -> Unit,
    onAutorChange: (String) -> Unit,
    onAnioChange: (String) -> Unit,
    onEjemplaresChange: (String) -> Unit,
    onRegistrar: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ValidatedTextField(
                formulario.titulo,
                onTituloChange,
                "Título",
                formulario.errorTitulo,
                Modifier.fillMaxWidth()
            )
            ValidatedTextField(
                formulario.autor,
                onAutorChange,
                "Autor",
                formulario.errorAutor,
                Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ValidatedTextField(
                    formulario.anio,
                    onAnioChange,
                    "Año",
                    formulario.errorAnio,
                    Modifier.weight(1f),
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                ValidatedTextField(
                    formulario.ejemplares,
                    onEjemplaresChange,
                    "Ejemplares",
                    formulario.errorEjemplares,
                    Modifier.weight(1f),
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Button(
                onClick = onRegistrar,
                enabled = !registrando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (registrando) "Registrando…" else "Registrar")
            }
        }
    }
}

@Composable
private fun LibroCard(libro: LibroUi) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(libro.titulo, style = MaterialTheme.typography.titleMedium)
            Text(libro.autor)
            Text(libro.lineaSecundaria)
            if (libro.requiereReposicion) {
                AssistChip(onClick = {}, label = { Text("Pocos ejemplares") })
            }
        }
    }
}
