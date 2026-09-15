# Capturas requeridas

Guarda en esta carpeta las capturas reales de la aplicación Android con los siguientes nombres. Antes de capturar, comprueba que se vean la barra superior y el contenido principal completo.

1. `01-inicio-claro.png`: abre **Inicio**, deja desactivado **Modo oscuro** y muestra la portada junto con los tres accesos rápidos.
2. `02-inicio-oscuro.png`: abre el menú lateral, activa **Modo oscuro**, ciérralo y captura la misma portada.
3. `03-libros-cargando.png`: desde una instalación o proceso recién iniciado, entra de inmediato en **Libros** mientras se ven el indicador y el texto **Cargando catálogo…**.
4. `04-libros-sin-libros.png`: con el repositorio de libros vacío, abre **Libros** y captura el estado **Sin libros**.
5. `05-libros-con-libros.png`: registra al menos un libro válido y captura el conteo y su tarjeta; conviene usar 2 ejemplares para que también aparezca **Pocos ejemplares**.
6. `06-libros-error.png`: provoca temporalmente un fallo en `LibroRepositorioEnMemoria.listar()`, abre **Libros** y captura el estado de error con **Reintentar**. Revierte inmediatamente ese cambio y verifica `git diff` antes de continuar; nunca lo confirmes ni lo subas.
7. `07-lectores-telefono-ausente.png`: registra un lector válido dejando vacío **Teléfono (opcional)** y captura su tarjeta con **No registrado**.
8. `08-prestamos.png`: abre **Préstamos** y captura el icono, el título **Préstamos en construcción** y su descripción.

No se incluyen imágenes fabricadas. Las ocho capturas deben obtenerse desde la aplicación en ejecución y revisarse antes de la entrega.
