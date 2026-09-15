# Parte II — BiblioMobil

## 1. Incorporación de un backend REST

Cambiarían las implementaciones de `LibroRepository` y `LectorRepository` ubicadas actualmente en `data/repository`, porque las clases en memoria serían reemplazadas por repositorios REST; también se agregarían en `data` el cliente HTTP, los DTO y sus mapeos, y `di/AppModule.kt` pasaría a enlazar las interfaces con esas nuevas implementaciones. Permanecerían intactos los modelos y contratos de `domain`, los casos de uso, los `ViewModel`, los `UiState` y las pantallas de `presentation`, porque las capas externas pueden depender del dominio, pero el dominio no debe conocer `data`; así, el cambio de origen de datos queda detrás de las interfaces.

## 2. Motivo para recibir año y ejemplares como `String`

Al recibir `anio` y `ejemplares` como `String`, `RegistrarLibroUseCase` puede distinguir un campo vacío de uno no numérico y devolver los mensajes exactos “El año es obligatorio” o “El año debe ser un número entero”, y lo mismo para ejemplares. Si recibiera `Int`, esa información original se perdería y la pantalla o el `ViewModel` tendría que convertir el texto antes de llamar al caso de uso; eso trasladaría una regla de entrada y validación fuera del dominio, duplicaría decisiones y haría que la interfaz tuviera que inventar cómo representar una conversión fallida.

## 3. Efecto de registrar `LibroRepository` como `factory`

Con `factory`, Koin crearía un `LibroRepositorioEnMemoria` nuevo cada vez que resolviera `LibroRepository`; entonces `RegistrarLibroUseCase` podría guardar un libro en una instancia mientras `ListarLibrosUseCase` consultaría otra instancia vacía. El usuario vería que el mensaje de registro aparece, pero el catálogo vuelve a “Sin libros” o pierde los registros al reconstruirse dependencias; con `single`, ambos casos de uso comparten la misma lista y los datos permanecen durante la ejecución.

## Evidencia de pruebas automatizadas

Comando ejecutado en Windows:

```text
.\gradlew.bat :shared:testAndroidHostTest --rerun-tasks --console=plain

> Task :shared:compileAndroidHostTest
> Task :shared:testAndroidHostTest

BUILD SUCCESSFUL in 8s
32 actionable tasks: 32 executed
Configuration cache entry reused.
```

Gradle no imprime el número de casos en esa salida. El conteo se verificó en los XML reales de `shared/build/test-results/testAndroidHostTest`: **31 pruebas, 0 fallos y 0 errores**.
