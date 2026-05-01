<div align="center">
  <h1>Práctica 4.- Uso de las Expresiones Regulares y Extensión de Software para Convertir Expresiones Regulares</h1>
   <p><i>Juárez Velázquez Erick Daniel 2025630052</i></p>
   <p><i>Placencia Murguia Juan Emilio 2025630024</i></p>
</div>

## Introducción
Las **Expresiones Regulares (ER)** son una de las herramientas más importantes dentro de la Teoría de la Computación. Dentro de la Jerarquía de Chomsky representan la parte algebraica de los lenguajes regulares, y lo que las hace especialmente interesantes es que son equivalentes a los Autómatas Finitos, tanto Deterministas (AFD) como No Deterministas (AFND), algo que queda demostrado formalmente con el Teorema de Kleene. En términos teóricos, una expresión regular permite definir un lenguaje de forma recursiva usando tres operaciones: unión, concatenación y cerradura de Kleene (∗), lo que facilita describir patrones de cadenas sin necesidad de dibujar estados y transiciones. Esta práctica se centra en implementar el algoritmo de eliminación de estados, que básicamente permite convertir un AFD en su expresión regular equivalente, reforzando así la idea de que ambas representaciones describen lo mismo. 


## Instrucciones de Compilación y Ejecución

Para evitar problemas con dependencias o recursos, se recomienda ejecutar el proyecto directamente desde la terminal. El código puede abrirse en IDEs como NetBeans, aunque puede generar errores inesperados, a menos que se tenga todo actualizado.

### Requisitos Previos

* Java JDK/JRE: Verificar de tener instalada la versión 25 o superior. 

### Ejecución desde la Terminal

1. Abre en la terminal o consola de comandos abre la dirección en donde se encuentra el archivo llamado Lenguajes-1.0-SNAPSHOT.jar
2.  Por último, ejecuta la aplicación utilizando el siguiente comando:

``` bash
java -jar Lenguajes-1.0-SNAPSHOT.jar 
```

### 1. Preparación de la Estructura Base (Autómata, Estados y Transiciones)

El primer paso fue adaptar las clases base de entregas anteriores (`Automata.java`, `Estado.java` y `Transicion.java`), ya que el algoritmo de conversión requiere que las etiquetas de las transiciones dejen de ser símbolos simples y pasen a ser expresiones regulares completas. Los cambios principales fueron: 
*  **Adaptación de Transiciones:** Se modificó `Transicion` para que su atributo soporte cadenas de texto (`String`), permitiendo almacenar resultados como `(0|1)*1`. 
*  **Gestión de Identidad de Estados:** Se sobreescribieron `equals` y `hashCode` en `Estado` para garantizar identificación única, lo cual es necesario para evitar errores al eliminar nodos iterativamente. 
*  **Estructura de Normalización:** Se preparó la lógica para insertar un estado inicial único y un estado final único, conectados por transiciones λ, requisito necesario antes de iniciar el algoritmo.

### 2. Importación y Procesamiento de Archivos JFLAP (.jff) 
Se implementó un módulo de entrada/salida (`IOAutomata.java`) para leer archivos generados por JFLAP. Como los archivos `.jff` están en formato XML, se usó `javax.xml.parsers.DocumentBuilder` para procesarlos. Las funciones principales del módulo son: 
*  **Mapeo de Nodos XML:** Extracción de las etiquetas `<state>` y `<transition>` y su conversión a los objetos `Estado` y `Transicion` del programa. 
*  **Gestión de Transiciones Vacías:** Si la etiqueta `<read>` está vacía (como lo hace JFLAP por defecto para transiciones epsilon), el sistema la interpreta automáticamente como `λ`. 
* **Verificación de AFD Válido:** Una vez importado el modelo, se valida que no existan no-determinismos antes de habilitar la conversión a Expresión Regular.  (Ver Figura 1)
 <p align="center">
  <img src="https://github.com/user-attachments/assets/6f0c0f6b-a707-49be-a11c-58dda668559b">
  <br>
  Figura 1
</p>
 
 
 ### 3. Conversión de AFD a ER (Algoritmo de Eliminación de Estados) 
 
La parte central de la práctica fue implementar el algoritmo de eliminación de estados en la clase `ConvertidorER`. El procedimiento sigue estos pasos: 
1.  **Normalización:** Se añade un estado inicial único ($q_{in}$) y un estado final único ($q_{fi}$), conectados al autómata original con transiciones vacías ($\lambda$). 
2.  **Reducción y Teorema de Kleene:** Se eliminan iterativamente los estados intermedios $k$, actualizando las transiciones entre predecesores ($i$) y sucesores ($j$) con la fórmula: $$R_{nueva} = R_{ij} \mid (R_{ik} \cdot (R_{kk})^* \cdot R_{kj})$$ Para hacerlo más visual, el proceso está integrado al `Lienzo`, permitiendo ver paso a paso cómo desaparecen los estados y cómo se actualizan las expresiones regulares en las flechas en tiempo real.

 <p align="center">
  <img src="https://github.com/user-attachments/assets/85d8cfab-bcf5-4b46-ba7f-b5a60665404e">
  <br>
  Figura 2
</p>

A continuación se presenta un serie de capturas en las cuales se muestra el funcionamiento y resultado del software, mismas se presentan en las figuras 3,4,5,6 y 7.
 <p align="center">
  <img src="https://github.com/user-attachments/assets/47054b90-2483-4c94-bc19-6b16564eb60f">
  <br>
  Figura 3
</p>
 <p align="center">
  <img src="https://github.com/user-attachments/assets/edd42a50-0ffb-44bd-b583-c7163b8095d6">
  <br>
  Figura 4
</p>
 <p align="center">
  <img src="https://github.com/user-attachments/assets/4667761e-59e3-4a84-bca8-ab184afb8fde">
  <br>
  Figura 5
</p>
 <p align="center">
  <img src="https://github.com/user-attachments/assets/b54efc4e-3a47-454e-85d2-dc2ec6a6ab30">
  <br>
  Figura 6
</p>
 <p align="center">
  <img src="https://github.com/user-attachments/assets/e779fb89-4e9b-4341-afa3-d9ec3cafaaa4">
  <br>
  Figura 7
</p>

Concluyendo con lo anterior, se muestra la forma correcta de que la conversión de AFD a ER es correcta y es funcional.


## Conclusión

A lo largo de esta práctica se logró conectar la teoría vista en clase con algo tangible, ya que implementar el algoritmo de eliminación de estados y comprobar el Teorema de Kleene de forma práctica dejó mucho más claro por qué los autómatas y las expresiones regulares describen exactamente el mismo tipo de lenguajes. La integración con JFLAP sumó un reto adicional en cuanto al diseño del software, pero también hizo el trabajo más interesante al acercarlo a situaciones reales. Sin embargo, lo que terminó de darle sentido a toda la práctica fue el módulo de validación, pues ver que lo desarrollado puede usarse para verificar correos, números telefónicos y contraseñas demostró que los autómatas finitos tienen aplicaciones concretas y relevantes más allá del salón de clases.
