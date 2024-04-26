/*

Programa:
	pixels_difusion.s
Compilación:
	as --32 -gstabs -I include -o pixels.o pixels_difusion.s
Descripción:
	Generar un array de 2D (coordenadas de los pixels ) para almacenar una estructura de datos RGB (3ª dimensión) e inicializarlo. 
Metodogía:
	Desarrollar el algoritmo en lenguaje C
	Relacionar las estructuras de C con las de Ensamblador:
	Primero generamos la segunda dimensión 
	Después generamos la primera dimensión con un array de direcciones de memoria sin inicializar:
	A continuación inicializamos el array de pixels
Espacio de direcciones:
	El origen de coordenadas está en la esquina inferior izquierda.
	El contenido del buffer comienza por el origen de coordenadas y contiene filas secuenciales
	La estructura del pixel sigue la seguencia B-G-R

*/

/*
 Correspondencia tuple 3D (Fila x_coor ,Columna y_coor, Offset RGB) -> buffer lineal
   Coordenada Filas    :(0,Nx-1)
   Coordenada Columnas :(0,Ny-1)
   Offset RGB (0,1,2)
 Buffer (direcciona bytes):
   Elemento del buffer: cada elemento son 3 bytes.
   Espacio de elementos - Espacio bytes: la dirección de un elemento es el byte AZUL de dicho elemento.
   1º elemento de cada fila i    : 1Fi -> x_coor * DIMENSION_Y * DIMENSION_Z
   1º elemento de cada columna j : 1Cj -> y_coor * DIMENSION_Z
   Posición del color:         PC (B->0, G->1, R->2)
 Función correspondencia del pixel (i,j) con el elemento (Offset Pixel (i,j)) en el buffer: OPij -> 1Fi + 1Cj 
 Ejemplo: Array XY  512x512, al pixel (3,3) le corresponde el siguiente Offset:
	  1F=3*512*3=4608
	  1C=3*3    =9
	  OP= 4608+9=4617
*/

	
	## Sección MACROS
	.include "macros_file.inc"
	## Sección variables scope fichero inicializadas
	.section .data
rojo:	.byte 0
verde:	.byte 0
azul:	.byte 0
origen_x:	.4byte  0 ## Origen coordenada X del cuadrado en pixels
origen_y:	.4byte  0 ## Origen coordenada Y del cuadrado en pisels
lado:		.4byte  0 ## Lado del cuadrado en pixels
proporcion:	.4byte  0 ## tanto por cien de los tres colores primarios RGB
dimension:	.4byte  0 ## Dimension del cuadrado background en pixels
buffer_ptr:  	.4byte  0 ## variable puntero donde guardaré el argumento referencia que le pasa main desde C
fin_x:		.4byte  0 ## Limite coordenada X del cuadrado en pixels=origen_x+lado-1
fin_y:	        .4byte  0 ## Limite coordenada Y del cuadrado en pixels=origen_y+lado-1
	## Sección Instrucciones
	.section .text
	.global pixels_generator
	## 	.global buffer
	.type pixels_generator, @function
pixels_generator:
        PROLOGO
	SALVAR_CONTEXTO
	CAPTURAR_ARGUMENTOS
	INICIAR_COLOR
	INICIAR_COOR_LIMITES	
	## BUCLE de asignación de valores pixels del buffer.
	mov origen_x,%edi #inicio Filas
fila:
	mov origen_y,%esi #inicio Columnas
columna:	
        ## control columna
	cmp fin_y,%esi
	jz  col_exit
	## actualización columna
	inc %esi
	PX_POSICION_BUFFER
	PX_COLOR
	## siguiente columna
	jmp columna
col_exit:
	## control fila
	cmp fin_x,%edi
	jz fil_exit
	## actualización fila
	inc %edi
	PX_POSICION_BUFFER
	PX_COLOR
	## siguiente fila
	jmp fila
fil_exit:	
	RECU_CONTEXTO
	EPILOGO
	ret
	.end


	


	
