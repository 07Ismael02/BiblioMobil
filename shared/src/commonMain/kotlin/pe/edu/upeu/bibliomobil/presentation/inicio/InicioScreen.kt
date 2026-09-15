package pe.edu.upeu.bibliomobil.presentation.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import pe.edu.upeu.bibliomobil.navigation.Screen

private data class AccesoRapido(
    val titulo: String,
    val icono: ImageVector,
    val destino: Screen
)

private val ACCESOS_RAPIDOS = listOf(
    AccesoRapido("Registrar libros", Icons.AutoMirrored.Filled.MenuBook, Screen.Libros),
    AccesoRapido("Registrar lectores", Icons.Default.PersonAdd, Screen.Lectores),
    AccesoRapido("Revisar préstamos", Icons.Default.Bookmark, Screen.Prestamos)
)

@Composable
fun InicioScreen(onNavegar: (Screen) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.LocalLibrary,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("BiblioMobil", style = MaterialTheme.typography.headlineMedium)
                Text("Tu biblioteca, siempre disponible")
            }
        }
        item {
            Text("Qué puedes hacer", style = MaterialTheme.typography.titleLarge)
        }
        items(ACCESOS_RAPIDOS) { acceso ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavegar(acceso.destino) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(acceso.icono, contentDescription = null)
                    Text(acceso.titulo, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
