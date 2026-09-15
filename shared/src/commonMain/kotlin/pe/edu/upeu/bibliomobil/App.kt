package pe.edu.upeu.bibliomobil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.upeu.bibliomobil.navigation.DESTINOS
import pe.edu.upeu.bibliomobil.navigation.Screen
import pe.edu.upeu.bibliomobil.navigation.ScreenSaver
import pe.edu.upeu.bibliomobil.presentation.components.EstadoVacio
import pe.edu.upeu.bibliomobil.presentation.inicio.InicioScreen
import pe.edu.upeu.bibliomobil.presentation.lector.LectorScreen
import pe.edu.upeu.bibliomobil.presentation.libro.LibroScreen
import pe.edu.upeu.bibliomobil.theme.BiblioMobilTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    KoinContext {
        var pantallaActual by rememberSaveable(stateSaver = ScreenSaver) {
            mutableStateOf<Screen>(Screen.Inicio)
        }
        var darkTheme by rememberSaveable { mutableStateOf(false) }
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val destinoActual = DESTINOS.first { it.screen == pantallaActual }

        BiblioMobilTheme(darkTheme = darkTheme) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Column(modifier = Modifier.fillMaxHeight().padding(16.dp)) {
                            Text("BiblioMobil", style = MaterialTheme.typography.headlineSmall)
                            Text(
                                "Biblioteca Central",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            DESTINOS.forEach { destino ->
                                NavigationDrawerItem(
                                    label = { Text(destino.titulo) },
                                    selected = pantallaActual == destino.screen,
                                    onClick = {
                                        pantallaActual = destino.screen
                                        scope.launch { drawerState.close() }
                                    },
                                    icon = { Icon(destino.icono, contentDescription = destino.titulo) }
                                )
                            }
                            Spacer(Modifier.weight(1f))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Modo oscuro")
                                Switch(checked = darkTheme, onCheckedChange = { darkTheme = it })
                            }
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(destinoActual.titulo) },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Box(Modifier.fillMaxSize().padding(paddingValues)) {
                        when (pantallaActual) {
                            Screen.Inicio -> InicioScreen(onNavegar = { pantallaActual = it })
                            Screen.Libros -> LibroScreen(viewModel = koinViewModel())
                            Screen.Lectores -> LectorScreen(viewModel = koinViewModel())
                            Screen.Prestamos -> PrestamosScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PrestamosScreen() {
    EstadoVacio(
        icono = Icons.Default.Bookmark,
        titulo = "Préstamos en construcción",
        descripcion = "El módulo de préstamos estará disponible en una próxima versión.",
        modifier = Modifier.fillMaxSize()
    )
}
