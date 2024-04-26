	/*
Programa:	 array_pixels_2.s
Compilación:	as --32 -gstabs -o array_pixels_2.o array_pixels_2.s
Descripción:	 Generar un array de 2D (coordenadas de los pixels ) para almacenar una estructura de datos RGB (3ª dimensión) e inicializarlo. 
Metodogía:	 Desarrollar el algoritmo en lenguaje C
	Relacionar las estructuras de C con las de Ensamblador:
	Primero generamos la segunda dimensión 
	Después generamos la primera dimensión con un array de direcciones de memoria sin inicializar:
	A continuación inicializamos el array de punteros "  " con las direcciones de los pixels
	*/


	## variables globales
	## .global buffer ## buffer es o una variable global o un argumento

	## Sección Macros
	.equ DIMENSION_Z, 3		#tamaño de la tercera dimensión: estructura pixel (r,g, b)
	## El origen de coordenadas está en la esquina inferior izquierda.
	## El contenido del buffer comienza por el origen de coordenadas y contiene filas secuenciales
	## La estructura del pixel sigue la seguencia B-G-R

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
	## Nuevo frame en la pila
	push %ebp
	mov %esp, %ebp
#################################################
### SALVO EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	push %eax
	push %ebx
	push %ecx
	push %edx
	push %edi
	push %esi
	
################################################# pixels_generator_2(ORIGEN_X,ORIGEN_Y,LADO,PROPORCION,DIMENSION,buffer);
	## Capturo el argumento origen_x //en una misma instrucción no se puede acceder dos veces a memoria -> dos instrucciones
	movl $0,origen_x
	## Capturo el argumento origen_y
	movl $0,origen_y
	## Capturo el argumento lado
	mov 8(%ebp),%eax
	mov %eax,lado
	## Capturo el argumento proporcion
	movl $0x7f,proporcion
	## Capturo el argumento dimension
	mov 12(%ebp),%eax
	mov %eax,dimension
	## Capturo el argumento buffer
	mov 16(%ebp),%eax
	mov %eax,buffer_ptr   #inicializo el puntero con el argumento
	## intensidad colores: color INICIAL
	## color background ubuntu RGB is (48, 10, 36).
	movb proporcion,%al
	movb $0x00,rojo
	movb $0xff,verde
	movb $0x00,azul
	## Cálculo de los limites: origen_x+lado-1   ;    origen_y+lado-1
	mov origen_x,%eax
	add lado,%eax
	dec %eax
	mov %eax,fin_x
	mov origen_y,%eax
	add lado,%eax
	dec %eax
	mov %eax,fin_y

	## Bucle de inicialización del array
	mov origen_x,%edi		#inicio Filas


fila:
	mov origen_y,%esi		#inicio Columnas
columna:	

	## Reset registros aritmética
	xor %eax,%eax
	xor %ebx,%ebx
	xor %ecx,%ecx
	## Aritmética Fila
	movw %di,%bx		#posición fila
	imul dimension,%ebx
	imul $DIMENSION_Z,%ebx
	## Aritmética Columna
	movw %si,%cx		#posición columna
	imul $DIMENSION_Z,%ecx
	## Correspondencia array_pixel -> posición buffer
	xor %eax,%eax
	add %ebx,%eax
	add %ecx,%eax
	mov %eax,%ebx     #EBX contiene el offset del pixel en el buffer
	
	## Actualizar colores en el pixel
	xor %ecx,%ecx		#índice color
	movb azul,%al		#intensidad azul
        mov buffer_ptr,%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	inc %ecx
	movb verde,%al		#intensidad verde
	mov buffer_ptr,%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	inc %ecx
	movb rojo,%al		#intensidad rojo
	mov buffer_ptr,%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	
	## actualización posición columna
	inc %esi
	
	##     control columna
	cmp fin_y,%esi
	jz  col_exit

	## siguiente columna

	jmp columna
	
	## actualización posición fila
col_exit:
	##siguiente fila
	inc %edi

	cmp fin_x,%edi
	jz fil_exit
	
	## siguiente fila
	jmp fila
	
fil_exit:	


#################################################
### RESTAURO  EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	pop %esi
	pop %edi
	pop %edx
	pop %ecx
	pop %ebx
	pop %eax
#################################################
	
	## Recuperar el antiguo frame
	mov %ebp,%esp
	pop %ebp
	
	ret

	.end


	


	
