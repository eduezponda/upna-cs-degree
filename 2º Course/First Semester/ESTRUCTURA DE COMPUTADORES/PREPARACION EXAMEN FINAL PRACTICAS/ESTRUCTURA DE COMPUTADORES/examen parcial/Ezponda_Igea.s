### Estructura de Computadores. Examen 1º parcial curso 2022.
### Programación en lenguaje ensamblador AT&T para la arquitectura x86 32 bits 
### Programa: Apellido1_Apellido2.s, por ejemplo, Alen_Urra.s
###
#################################################################################
###                                                                           ###
###  Enunciado de la 1ª PARTE del programa apellido1_apellido2.s (6 pts)      ###
###                                                                           ###
#################################################################################
### Desarrollar el código ensamblador (3 pts):
###     La etiqueta de entrada al programa es main.
###     La etiqueta de salida del programa es fin.
###     Declarar la variable lista inicializandola con el string "Hola"
###     Utilizar la macro LONGITUD para definir el número de letras del array "Hola"
###	Convertir las letras del array lista de minúsculas a mayúsculas,
###     sabiendo que las letras mayúsculas tienen un código ASCII 0x20 menor que las minúsculas.
###	código ASCII de "a" = 0x61 y de "A"=0x41
### Preguntas sobre el depurador GDB (3 pts):
###    las respuestas han de incluir tanto los comandos GDB como el resultado de ejecutar dichos comandos.
###    a) (0.7 pts) imprimir el contenido del array lista en código texto con el comando print -> p
###    comando (gdb): p (char[4]) &lista
###    resultado: 
###
###    b) (0.7 pts) imprimir el contenido del array lista en código hexadecimal con el comando examine -> x
###    comando (gdb): x /4xb &lista
###    resultado: 
###
###    c) (0.7 pts) imprimir la dirección del array lista en código hexadecimal con el comando p
###    comando (gdb): p /a &lista
###    resultado: 
###
###    d) (1 pts) imprimir la dirección de tercer elemento del array lista en código hexadecimal con el comando p
###    comando (gdb): p /x (&lista+2)
###    resultado: 
###
###    e) (1 pts) imprimir el tercer elemento del array lista en código texto con el comando x
###    comando (gdb): +x /d (&lista+2)
###    resultado: 
###
############################################################################################
###                                                                                      ###
###        Enunciado de la 2ª PARTE del programa apellido1_apellido2.s (4 pts)           ###
###                                                                                      ###
############################################################################################
###     Desarrollar el código ensamblador:
###     Descripción: asignar el valor booleano TRUE a la variable A si se cumple la doble condición:
### 	doble condición -> A:=(B < C) OR (D != F)
### 	La variable A se implementará con el registro BL
### 	Lógica positiva: A=TRUE=1 ó A=FALSE=0
### 	Devuelve el valor de la variable A al Sistema Operativo
###     Valores de B,C,D,F con un tamaño de 4 bytes ->  B=-1, C=+1, D=-1  y F=+1
###     Utilizar todos los comentarios que se han especificado más abajo 
###     y que informan sobre el resultado del algoritmo durante la ejecución del mismo.
###     La función de los comentarios ayuda a la lectura y evaluación del código.
###     Completar el código que falta a continuación de los mensajes.
 
	.EQU LONGITUD, 4
	.section .rodata
saludo:	.string "Estructura de Computadores curso 2022. UPNA 28 de Octubre"
men1F:	.string "Primera condición falsa"
men1V:	.string "Primera condición verdadera"
men2F:	.string "Segunda condición falsa"
men2V:	.string "Segunda condición verdadera"
menAF:  .string "La condición A es falsa"
menAV:  .string "La condición A es verdadera"

	.section .data
lista:	.string "Hola"
A:	.int
B:	.int  -1
C:	.int   1
D:	.int  -1
F:	.int   1

	
	.global main
	.section .text
	
        # Comienzo del programa con el mensaje de saludo
main: 	push $saludo
		call puts
####################################################################
##                                                                ##
##                Código de la 1ª Parte                           ##
##                                                                ##
####################################################################
        # Convertir el string lista en mayúsculas
		movl $1,%eax
        jmp bucle
        
bucle:	subb $0x20,lista(,%eax,)
		addl $1, %eax
		cmp $LONGITUD ,%eax
		jne bucle
		
	# Imprimir el resultado de la conversión 
		
salida1: push $lista
         call puts
####################################################################
##                                                                ##
##              Código de la   2ª Parte                           ##
##                                                                ##
####################################################################
        # Código para comprobar la condición (B < C)
        mov C, %eax
        cmpl B, %eax
		jl true1 
		jae	false1
	
	# Imprimir el mensaje men1V, ya que se cumple la primera condición
true1:	push $men1V
		call puts
		jmp resultado1
		
       # Imprimir el mensaje men1F, ya que NO se cumple la primera condición
false1:	push $men1F
		call puts
		jmp resultado1
        # Código para comprobar la condición (D != F)	
resultado1:        mov F, %eax
		cmpl D,%eax
		je	false2
		jne true2

	# Imprimir el mensaje men2V, ya que se cumple la segunda condición
true2:	push $men2V
		call puts
		jmp resultado2

	# Imprimir el mensaje men2F, ya que NO se cumple la segunda condición
false2:	push $men2F
		call puts
		jmp resultado2

        # Código para comprobar la condición (B < C) OR (D != F)	
resultado2:     mov C, %eax
		cmpl B,%eax
		jl true3
		mov F, %eax
		cmp	D,%eax
		jne true3
		je	false3

        # Imprimir mensaje menAV ya que la variable A es verdadera
true3:	push $menAV
		call puts
		movl $1,A
		movb A,%bl
		jmp fin

        # Imprimir mensaje menAF# ya que la variable A es falsa
false3:	push $menAF
		call puts
		movl $0,A
		movb A, %bl
	    

fin:    	movl A,%ebx   ##movsbl %bl, %ebx 
		mov $1, %eax
		int $0x80


	.end
