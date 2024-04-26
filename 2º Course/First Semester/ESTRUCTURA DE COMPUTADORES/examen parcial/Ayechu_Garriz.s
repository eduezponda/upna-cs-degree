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
###    comando (gdb): p /s (char *)&lista
###    resultado: $2 = 0x56559008 "HOLA"
###
###    b) (0.7 pts) imprimir el contenido del array lista en código hexadecimal con el comando examine -> x
###    comando (gdb): x /5xb &lista
###    resultado: 0x56559008:     0x48    0x4f    0x4c    0x41    0x00
###
###    c) (0.7 pts) imprimir la dirección del array lista en código hexadecimal con el comando p
###    comando (gdb): p /ax &lista
###    resultado: $4 = 0x56559008
###
###    d) (1 pts) imprimir la dirección de tercer elemento del array lista en código hexadecimal con el comando p
###    comando (gdb): p /ax (char *)&lista+2
###    resultado: $5 = 0x5655900a
###
###    e) (1 pts) imprimir el tercer elemento del array lista en código texto con el comando x
###    comando (gdb): x /1cb (char *)&lista+2
###    resultado: 0x5655900a:     76 'L'
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
### 	Devuelve el valor de la variable A al Sistal, %alema Operativo
###     Valores de B,C,D,F coriable lista inicializandoln un tamaño de 4 bytes ->  B=-1, C=+1, D=-1  y F=+1
###     Utilizar todos los comentarios que se han especificado más abajo 
###     y que informan sobre el resultado del algoritmo durante la ejecución del mismo.
###     La función de los comentarios ayuda a la lectura y evaluación del código.
###     Completar el código que falta a continuación de los mensajes.
 
    ## DEFINICION DE MACROS
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
    
lista:  .string "Hola"
B:  .int -1
C:  .int 1
D:  .int -1
F:  .int 1
    
    .section .text
    .global main

####################################################################
##                                                                ##
##                Código de la 1ª Parte                           ##
##                                                                ##
####################################################################
main:
    # Comienzo del programa con el mensaje de saludo
	push $saludo
	call puts
    # Convertir el string lista en mayúsculas
    xor %eax, %eax  #inicialización registro eax
    add $1, %eax #saltamos el primer caracter, ya inicializado en mayuscula ('H')
    jmp bucle   # salto a etiqueta bucle, que convierte minusculas en mayusculas hasta que busque caracter NULL (0x00)

bucle:
    cmpb $0x00, lista(,%eax, 1) #comprobamos si es caracter NULL (0x00)
    je salida1  # si es caracter NULL, salto a salida, fin del bucle
    subb $0x20, lista(,%eax,1)  # si no es caracter NULL, convertir minuscula en mayuscula
    add $1, %eax    # incrementar en 1 el registro eax para que acceder siguiente posicion de lista
    jmp bucle   #salto a bucle, repetir proceso
        
	# Imprimir el resultado de la conversión        
salida1:
    push $lista
    call puts
####################################################################
##                                                                ##
##              Código de la   2ª Parte                           ##
##                                                                ##
####################################################################
    # Inicialización de Registro EAX, BL, BH
    xor %eax, %eax
    xor %bl, %bl
    xor %bh, %bh
    
    # Código para comprobar la condición (B < C)
    mov B, %eax #mover contenido de direccion B a registro EAX
    cmp C, %eax #compare contenido de direccion C con contenido registro eax
    mov $0, %bl #0 representa FALSE
    jge N1  #salto a N1 si B >= C-> no se cumple primera condicion B < C
    
	# Imprimir el mensaje men1V, ya que se cumple la primera condición
	push $men1V
	call puts
	mov $1, %bl
	jmp N3
    # Imprimir el mensaje men1F, ya que NO se cumple la primera condición
N1:
    push  $men1F
    call puts
    
    # Código para comprobar la condición (D != F)	
N2: 
    mov D, %eax
    cmp F, %eax
    mov $0, %bh
    je N4
    jmp N5
    
	# Imprimir el mensaje men2V, ya que se cumple la segunda condición
N4:
    push $men2V
    call puts
    jmp N3

	# Imprimir el mensaje men2F, ya que NO se cumple la segunda condición
N5:
    push $men2F
    call puts
    jmp N3
    # Código para comprobar la condición (B < C) OR (D != F)	
N3:
    or %bh, %bl
    jz N6
    # Imprimir mensaje menAV ya que la variable A es verdadera
    push menAV
    call puts
    jmp fin
    # Imprimir mensaje menAF# ya que la variable A es falsa
N6:
    push menAF
    call puts

fin:
    movsbl %bl, %ebx    #devuelve al sistema el contenido de registro bl, resultado de operacion A:=(B < C) OR (D != F)
    
    mov $1, %eax
    int $0x80


	.end
