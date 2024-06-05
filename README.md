# OrdenApp - README
<p align="center">
  <img src="https://i.imgur.com/unwgorA.gif" alt="Demo GIF" width="500" height="500">
</p>

## Introducción

¡Bienvenido a OrdenApp! Esta aplicación está diseñada para facilitar la compra y venta de productos a través de una interfaz fácil de usar. La aplicación está construida utilizando Java y utiliza Firebase como base de datos para proporcionar características como listado de productos, funcionalidad de búsqueda, gestión de carrito de compras y autenticación de usuarios.

## Funciones

- **Registro y Autenticación de Usuarios**: Regístrate y accede a tu cuenta de forma segura.
- **Listado de Productos**: Navega y visualiza información detallada sobre los productos disponibles.
- **Funcionalidad de Búsqueda**: Encuentra productos fácilmente usando la función de búsqueda.
- **Carrito de Compras**: Añade productos a tu carrito y gestiona tus compras.
- **Gestión de Pedidos**: Realiza pedidos y consulta tu historial de pedidos.
- **Panel de Administración**: Los administradores pueden agregar, actualizar y eliminar productos.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **JavaFX**: Para construir la interfaz de usuario.
- **Firebase**: Base de datos en tiempo real para almacenar datos de usuarios y productos.
- **Firebase Authentication**: Para la autenticación segura de usuarios.
- **Maven**: Herramienta de automatización de compilación.
- **Git**: Sistema de control de versiones para gestionar el código fuente.
- **Android Studio**: Entorno de desarrollo integrado (IDE) para construir y probar la aplicación.

## Instalación

### Requisitos Previos

- Kit de Desarrollo de Java (JDK) 11 o superior
- Apache Maven
- Cuenta de Firebase y proyecto configurado
- Git instalado
- Android Studio instalado

### Pasos

1. **Clonar el Repositorio**
    ```bash
    git clone https://github.com/jeisonful/Ordenapp/
    cd ordenapp-app
    ```

2. **Configurar Firebase**
    - Ve a [Firebase Console](https://console.firebase.google.com/) y crea un nuevo proyecto.
    - Configura Firebase Authentication y Firestore Database.
    - Descarga el archivo `google-services.json` y colócalo en el directorio `src/main/resources`.

3. **Actualizar Configuración**
    - Actualiza la configuración de Firebase en `src/main/resources/firebase.properties` con los detalles de tu proyecto.

4. **Importar el Proyecto en Android Studio**
    - Abre Android Studio.
    - Selecciona "Import Project" y elige el directorio del proyecto clonado.
    - Android Studio configurará el proyecto automáticamente.

5. **Compilar el Proyecto**
    ```bash
    mvn clean install
    ```

6. **Ejecutar la Aplicación**
    ```bash
    mvn exec:java -Dexec.mainClass="com.ordenapp.Main"
    ```

## Uso

### Registro y Inicio de Sesión

1. Inicia la aplicación.
2. Haz clic en el botón "Registrarse" para crear una nueva cuenta.
3. Completa los detalles requeridos y envía el formulario.
4. Inicia sesión con tus nuevas credenciales.

### Navegación de Productos

- Navega a la sección "Productos" para ver todos los productos disponibles.
- Usa la barra de búsqueda para encontrar productos específicos.

### Gestión del Carrito de Compras

- Añade productos a tu carrito haciendo clic en el botón "Añadir al Carrito".
- Consulta tu carrito haciendo clic en el icono del "Carrito".
- Actualiza la cantidad o elimina artículos de tu carrito según sea necesario.

### Realización de Pedidos

- Una vez que tu carrito esté listo, haz clic en el botón "Finalizar Compra" para realizar tu pedido.
- Introduce la información de envío requerida y confirma tu pedido.

### Panel de Administración

- Inicia sesión como administrador para acceder al panel de administración.
- Gestiona productos agregándolos, actualizándolos o eliminándolos.

## Contribuir

Damos la bienvenida a contribuciones de la comunidad. Para contribuir:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tu característica o corrección de errores.
3. Realiza tus cambios y haz commit de ellos.
4. Sube tus cambios a tu fork.
5. Crea una solicitud de pull al repositorio principal.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Contacto

Para cualquier pregunta o problema, por favor contáctanos en jeisonful@gmail.com.

¡Gracias por usar OrdenApp!
