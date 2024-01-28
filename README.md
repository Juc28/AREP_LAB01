# LABORATORIO 01 DE AREP 

Una aplicación web que permite buscar información sobre películas  a través de sus títulos. La aplicación utiliza la API  OMDB gratuita e implementa un caché para evitar solicitudes repetidas a API externas.
Esta aplicación tiene muchos usuarios y utiliza HTTP como protocolo de comunicación.

# Empezando 
* Para poder correr el laboratorio se clona el repositorio en una máquina local con el siguiente comando 
  ```
  git clone https://github.com/Juc28/AREP_LAB01.git
  ```
  
* Para ejecutar el laboratrio es la clase [HttpServer](https://github.com/Juc28/AREP_LAB01/blob/master/Taller01/src/main/java/edu/escuelaing/arem/ASE/app/HttpServer.java) en donde se verá un index en el que se puede hacer la consulta de las películas
* En la carpeta [test](https://github.com/Juc28/AREP_LAB01/tree/master/Taller01/src/test/java/edu/escuelaing/arem/ASE/app) se encuentra la clase HttpConectionTest y HttpServerTest que si se desea correr desde la consola se puede con el siguiente comando:

  ```
  mvn test
  ```
# Funcionamiento 
Para iniciar el proyecto, vaya a la clase HTTPServer en el directorio Server. Luego, ejecute la clase y abra su navegador de confianza(En mi caso use [Google](https://www.google.com/?hl=es)). En la barra de direcciones, escriba  ``` localhost:35000 ```. Esto abrirá la aplicación web.La aplicación web tiene dos campos de entrada: uno para el método GET y otro para el método POST. Elija el método que desee y escriba el nombre de la película que desea consultar. Luego, haga clic en "Enviar". 

## Logica 
El servidor fachada recibe mensajes JSON de un servidor que se conecta al API de [OMDB](https://www.omdbapi.com). Si la búsqueda no está en el caché, el servidor fachada la realiza en el API de [OMDB](https://www.omdbapi.com).
Una vez que se obtiene la información de la búsqueda, se utiliza la biblioteca JSON de Java para convertirla a un objeto Java. Este objeto se puede utilizar para construir una lista de películas, que se puede mostrar en HTML de una manera fácil de leer.

## Posible extensibilidad
La aplicación utiliza un hashmap para almacenar la información de la búsqueda. Esto permite mostrar cualquier valor que se desee, ya que solo se necesita agregar el valor al hashmap.
Por ejemplo, si se desea mostrar la imagen de la película, se puede agregar la clave "Poster" al hashmap. Si se desea mostrar los actores de la película, se pueden agregar las claves "Actores" y "Fotos" al hashmap.
De esta manera, la aplicación es completamente extensible.

## Ejemplo de como se podria obtener un proveedor de servicios diferente
Para cambiar el proveedor de servicios, primero se debe agregar una nueva opción al HTML que permita seleccionar el nuevo proveedor. Los métodos que se utilizan para realizar las consultas son universales, por lo que no es necesario modificarlos.
Una vez que se ha agregado la nueva opción, se debe crear una nueva clase que se conecte al nuevo proveedor de servicios. Esta clase debe implementar la misma interfaz que la clase que se conecta al proveedor original.
Una vez que se ha creado la nueva clase, se debe actualizar la fachada para que se conecte a la nueva clase. Esto se puede hacer modificando el código de la fachada para que utilice la nueva clase en lugar de la clase original.
Una vez que se han realizado estos cambios, la aplicación podrá utilizar el nuevo proveedor de servicios.

# Herramientas 
- [MAVEN](https://maven.apache.org) : Para el manejo de las dependecias. 
- [GIT](https://git-scm.com) : Para el manejo de las versiones.
- [JAVA](https://www.java.com/es/) : Lenguaje de programación manejado. 

# Lincencia

Licenciado por GNU General Public License v3.0 [LICENSE](https://github.com/Juc28/AREP_LAB01/blob/master/LICENSE)

# Referencias 

- https://www.geeksforgeeks.org/concurrenthashmap-in-java/
- https://youtu.be/SkQL3eT3nTU?si=PNSJO0MC6aN-lB5g

# Autor 
Erika Juliana Castro Romero [Juc28](https://github.com/Juc28)
