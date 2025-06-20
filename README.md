# Pokédex virtual para Android
Aplicación en la que tendremos una Pokédex desde la que podremos ir capturando Pokémon. 


# Características principales
## Pantalla de Inicio de Sesión. 
La primera vez que iniciemos la aplicación deberemos de registrarnos para poder acceder a la app. 
Una vez hayamos iniciado sesión se mantendrá iniciada hasta que decidamos salir. 

![00](https://github.com/user-attachments/assets/17fb3aeb-8317-4a97-a007-d0497488791b)

## ⚠️ ⚠️ Nota importante ⚠️ ⚠️:
Para habilitar el inicio de sesión con Google en Firebase, es necesario registrar el SHA-1 del certificado en la consola de Firebase. 
Este valor se obtiene desde el entorno local de desarrollo y no está incluido en este repositorio por seguridad.

## Pantalla de Pokédex
Esta aplicación nos permite obtener los datos de los 150 primeros Pokemon de la API de Pokémon y cargarlos en una Pokedex.
Al seleccionar un Pokémon en la pantalla de Pokédex, éste será capturado.

![01](https://github.com/user-attachments/assets/79a04570-4f88-4a5f-9659-e8326e977c56)

## Pantalla de Pokémon capturados
Tendremos una pantalla para visualizar todos los Pokémon capturados.
Al seleccionar un Pokémon de la lista podremos visualizar algunas características del Pokémon, 
como el nombre, índice en la Pokédex, peso, altura, etc. 

![02](https://github.com/user-attachments/assets/789dfe75-5881-4ac6-b81d-ab27a0839982)

## Pantalla de detalles
En esta pantalla podremos revisar las características de cada Pokémon. 

![capturaDetalles](https://github.com/user-attachments/assets/9166a51a-d8c4-422b-9db4-9b4b853f9d43)

## Pantalla de ajustes
La aplicación implementa una pantalla desde la que se podrán cambiar algunos ajustes. 
Tendremos la opción de cambio de idioma, habilitar o deshabilitar la posibilidad de soltar Pokémon capturados,
un botón de información de la desarrolladora de la aplicación y un botón para cerrar sesión. 

![05](https://github.com/user-attachments/assets/d989e1a8-8bff-4b8c-8b78-d09397c0730e)

# Tecnologías utilizadas. 

Para el desarrollo de esta aplicación se destaca: 
* Uso de **Retrofit** para el acceso a la API de Pokémon en línea.
* Uso de **Firebase** para la Autenticación del usuario y guardado de datos en la nube.
* **Picasso** para insertar las imágenes desde una url de internet.
* **RecyclerView** para la implementación de las listas para la Pokédex y los Pokémon capturados.
* **Cardview** para el diseño de los elementos de cada RecyclerView.
* **SplashScreem** para implementar una pantalla flash personalizada.
  
# Instrucciones de uso.

Para ejecutar la app copia el enlace del repositorio en GitHub: <https://github.com/AnaRoGon/PokedexApp.git>

En el IDE de Android Studio selecciona en el menú; 
File -> New -> Project from Version Control y clica en la opción "Clone": 
El proyecto se cargará automáticamente y podrás ejecutarlo en el IDE. 

# Conclusiones del desarrollador.

Durante el desarrollo de esta tarea ha sido un reto ser capaz de manejar las llamadas a la API
teniendo en cuenta las anidaciones de la estructuras de datos de la API.
Además de separar la lógica de la aplicación para poder gestionar correctamente las funciones de cada
clase al obtener los datos anidados y evitar que se interbloqueasen.

Ha sido muy interesante conocer las posibilidades de Firebase y Retrofit. Las aplicaciones están estrechamente
ligadas al acceso a los datos desde la nube o desde internet y aprender su funcionamiento ha sido realmente interesante y motivador.
