package pe.edu.upeu.bibliomobil.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF234E70),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD6E8FA),
    onPrimaryContainer = Color(0xFF082F49),
    inversePrimary = Color(0xFFA7C8E8),
    secondary = Color(0xFF8A4F2D),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDBC8),
    onSecondaryContainer = Color(0xFF351000),
    tertiary = Color(0xFF755B00),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFE081),
    onTertiaryContainer = Color(0xFF241A00),
    background = Color(0xFFF8F9FC),
    onBackground = Color(0xFF191C20),
    surface = Color(0xFFF8F9FC),
    onSurface = Color(0xFF191C20),
    surfaceVariant = Color(0xFFDFE3E8),
    onSurfaceVariant = Color(0xFF43474D),
    surfaceTint = Color(0xFF234E70),
    inverseSurface = Color(0xFF2E3135),
    inverseOnSurface = Color(0xFFF0F1F5),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    outline = Color(0xFF73777E),
    outlineVariant = Color(0xFFC3C7CE),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFFF8F9FC),
    surfaceDim = Color(0xFFD8DADF),
    surfaceContainer = Color(0xFFECEEF2),
    surfaceContainerHigh = Color(0xFFE6E8EC),
    surfaceContainerHighest = Color(0xFFE1E2E7),
    surfaceContainerLow = Color(0xFFF2F3F7),
    surfaceContainerLowest = Color(0xFFFFFFFF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFA7C8E8),
    onPrimary = Color(0xFF073450),
    primaryContainer = Color(0xFF234E70),
    onPrimaryContainer = Color(0xFFD6E8FA),
    inversePrimary = Color(0xFF234E70),
    secondary = Color(0xFFFFB68F),
    onSecondary = Color(0xFF512300),
    secondaryContainer = Color(0xFF6D3818),
    onSecondaryContainer = Color(0xFFFFDBC8),
    tertiary = Color(0xFFE8C34F),
    onTertiary = Color(0xFF3D2F00),
    tertiaryContainer = Color(0xFF584500),
    onTertiaryContainer = Color(0xFFFFE081),
    background = Color(0xFF111418),
    onBackground = Color(0xFFE1E2E7),
    surface = Color(0xFF111418),
    onSurface = Color(0xFFE1E2E7),
    surfaceVariant = Color(0xFF43474D),
    onSurfaceVariant = Color(0xFFC3C7CE),
    surfaceTint = Color(0xFFA7C8E8),
    inverseSurface = Color(0xFFE1E2E7),
    inverseOnSurface = Color(0xFF2E3135),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    outline = Color(0xFF8D9198),
    outlineVariant = Color(0xFF43474D),
    scrim = Color(0xFF000000),
    surfaceBright = Color(0xFF37393E),
    surfaceDim = Color(0xFF111418),
    surfaceContainer = Color(0xFF1D2024),
    surfaceContainerHigh = Color(0xFF272A2E),
    surfaceContainerHighest = Color(0xFF323539),
    surfaceContainerLow = Color(0xFF191C20),
    surfaceContainerLowest = Color(0xFF0C0F12)
)

@Composable
fun BiblioMobilTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}
