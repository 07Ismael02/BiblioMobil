package pe.edu.upeu.bibliomobil

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import pe.edu.upeu.bibliomobil.presentation.lector.LectorScreen
import pe.edu.upeu.bibliomobil.presentation.libro.LibroScreen

@Composable
fun App() {
    KoinContext {
        LibroRoute()
    }
}

@Composable
private fun LibroRoute(modifier: Modifier = Modifier) {
    LibroScreen(viewModel = koinViewModel(), modifier = modifier)
}

@Composable
private fun LectorRoute(modifier: Modifier = Modifier) {
    LectorScreen(viewModel = koinViewModel(), modifier = modifier)
}
