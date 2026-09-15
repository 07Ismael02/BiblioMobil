package pe.edu.upeu.bibliomobil.presentation.lector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pe.edu.upeu.bibliomobil.presentation.components.EstadoVacio
import pe.edu.upeu.bibliomobil.presentation.components.MensajeExito
import pe.edu.upeu.bibliomobil.presentation.components.ValidatedTextField

@Composable
fun LectorScreen(viewModel: LectorViewModel, modifier: Modifier = Modifier) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Cartera de lectores", style = MaterialTheme.typography.headlineSmall)
        FormularioLectorCard(
            formulario = estado.formulario,
            registrando = estado.registrando,
            onNombreChange = viewModel::onNombreChange,
            onCorreoChange = viewModel::onCorreoChange,
            onTelefonoChange = viewModel::onTelefonoChange,
            onRegistrar = viewModel::registrar
        )
        estado.mensajeExito?.let {
            MensajeExito(it)
        }

        when (val fase = estado.fase) {
            FaseLectores.Cargando -> Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Cargando lectores…", modifier = Modifier.padding(top = 8.dp))
            }

            FaseLectores.SinLectores -> EstadoVacio(
                icono = Icons.Default.People,
                titulo = "Sin lectores",
                descripcion = "Aún no hay lectores registrados.",
                modifier = Modifier.fillMaxWidth().weight(1f)
            )

            is FaseLectores.ConLectores -> {
                Text(
                    if (fase.lectores.size == 1) "1 lector" else "${fase.lectores.size} lectores",
                    style = MaterialTheme.typography.titleMedium
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(fase.lectores, key = { it.id }) { lector -> LectorCard(lector) }
                }
            }

            is FaseLectores.Error -> EstadoVacio(
                icono = Icons.Default.People,
                titulo = fase.mensaje,
                descripcion = "Intenta cargar los lectores nuevamente.",
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = MaterialTheme.colorScheme.error,
                accion = {
                    Button(onClick = viewModel::cargarLectores) {
                        Text("Reintentar")
                    }
                }
            )
        }
    }
}

@Composable
private fun FormularioLectorCard(
    formulario: FormularioLector,
    registrando: Boolean,
    onNombreChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onRegistrar: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ValidatedTextField(
                formulario.nombre,
                onNombreChange,
                "Nombre",
                formulario.errorNombre,
                Modifier.fillMaxWidth()
            )
            ValidatedTextField(
                formulario.correo,
                onCorreoChange,
                "Correo",
                formulario.errorCorreo,
                Modifier.fillMaxWidth(),
                KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            ValidatedTextField(
                formulario.telefono,
                onTelefonoChange,
                "Teléfono (opcional)",
                formulario.errorTelefono,
                Modifier.fillMaxWidth(),
                KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
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
private fun LectorCard(lector: LectorUi) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(lector.nombre, style = MaterialTheme.typography.titleMedium)
            Text(lector.correo)
            Text(lector.telefono)
        }
    }
}
