# Introducción
Aplicación en la que tendremos una Pokédex desde la que podremos ir capturando Pokémon. 


# Características principales
## Pantalla de Inicio de Sesión. 
La primera vez que iniciemos la aplicación deberemos de registrarnos para poder acceder a la app. 
Una vez hayamos iniciado sesión se mantendrá iniciada hasta que decidamos salir. 

## Pantalla de Pokédex
Esta aplicación nos permite obtener los datos de los 150 primeros Pokemon de la API de Pokémon y cargarlos en una Pokedex.
Al seleccionar un Pokémon en la pantalla de Pokédez éste será capturado. 

## Pantalla de Pokémon capturados
Tendremos una pantalla para visualizar todos los Pokémon capturados.
Al seleccionar un Pokémon de la lista podremos visualizar algunas características del Pokémon, 
como el nombre, índice en la Pokédex, peso, altura, etc. 

## Pantalla de ajustes
La aplicación implementa una pantalla desde la que se podrán cambiar algunos ajustes. 
Tendremos la opción de cambio de idioma, habilitar o deshabilitar la posibilidad de soltar Pokémon capturados,
un botón de información de la desarrolladora de la aplicación y un botón para cerrar sesión. 

# Tecnologías utilizadas. 

Para el desarrollo de esta aplicación se destaca: 
* Uso de **Retrofit** para el acceso a la API de Pokémon en línea.
* Uso de **Firebase** para la Autenticación del usuario y guardado de datos en la nube.
* **Picasso** para insertar las imágenes desde una url de internet.
* **RecyclerView** para la implementación de las listas para la Pokédex y los Pokémon capturados.
* **Cardview** para el diseño de los elementos de cada RecyclerView.
* **SplashScreem** para implementar una pantalla flash personalizada.
  
# Instrucciones de uso.

Para ejecutar la app copia el enlace del repositorio en GitHub: <https://github.com/AnaRoGon/pmdm03.git>

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

# Ejemplo de funcionamiento.

Puedes acceder desde este enlace a un vídeo en el que se muestra el funcionamiento de la aplicación: 
<https://drive.google.com/file/d/1hwZQG-jVVVQY4DVmXkIID9lnX8o-WsGP/view?usp=drive_link>

# Algunas capturas de la aplicación

## Pantalla de login 
![00](https://github.com/user-attachments/assets/2acc63e7-a51b-4c2b-b2dc-1683bb8ad5e5)

## Pantalla de Pokedex
![01](https://github.com/user-attachments/assets/9af3b2ea-bba1-45f8-a89d-3b16c2f36553)

## Pantalla de lista de Pokémon capturados
![02](https://github.com/user-attachments/assets/71b68a97-d139-4698-b380-bcd9c3cbdb99)

## Pantalla de detalles del Pokémon
![03](https://github.com/user-attachments/assets/2a5dc738-7dd7-4ee0-9a16-4832e3fb9a88)

## Pantalla de ajustes con diálogo de información del autor
![05](https://github.com/user-attachments/assets/f32e7e61-9f7e-4eca-a96b-39d634392fe0)

