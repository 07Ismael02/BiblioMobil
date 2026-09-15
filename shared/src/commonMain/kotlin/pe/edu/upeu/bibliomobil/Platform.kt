package pe.edu.upeu.bibliomobil

// [NO TOCAR SI NO ES NECESARIO] Ejemplo expect/actual de Kotlin Multiplatform.
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
