### Estructura de Computadores. Examen 1º parcial curso 2022.
### Programación en lenguaje ensamblador AT&T para la arquitectura x86 32 bits
### Programa: Sola_Bienzobas.s
### 1ª parte del programa Sola_Bienzobas.s (3 pts)
###     Declarar la variable mensaje inicializandola con el caracter "m"
###	Convertir el caracter "m" en una mayúscula "M"
###     sabiendo que las letras mayúsculas tienen un código ASCII 0x20 menor que las minúsculas.
###	código ASCII de 'a' = 0x61 y de 'A'=0x41
### Preguntas sobre el depurador GDB (4 pts):
###    las respuestas han de incluir tanto los comandos GDB como el resultado de ejecutar dichos comandos.
###    a) imprimir el contenido de ECX con el comando print -> p
###    comando (gdb):	p $ecx
###    resultado:	$1 = valor(valor en ese momento en decimal)
###
###    b) imprimir el contenido de X en código hexadecimal con el comando examine -> x
###    comando (gdb):	x /xb &X
###    resultado:	dirección(de X): 0xfe(de 1 byte de X en hexadecimal)
###
###    c) imprimir la dirección de X en código hexadecimal con el comando p
###    comando (gdb):	p &X
###    resultado:	dirección(de X)
##
###
### 2ª parte del programa Sola_Bienzobas.s (3 pts):
### 	Descripción: programar el siguiente algoritmo condicional
### 	Lógica positiva: Z=TRUE=1 ó Z=FALSE=0
### 	La variable Z se implementará con el registro DL
### 	Devuelve el valor de la variable Z al Sistema Operativo
###     Valor de las variables X e Y con un tamaño de 1 byte -> X=-3 e Y=-1
###     Z=TRUE si el producto lógico XY tiene un número par de unos
###     Utilizar todos los mensajes que informan sobre el resultado del algoritmo durante la ejecución del mismo. NO cambiar ni añadir ningún mensaje.

	# sección rodata: read only data -> variables de solo lectura -> constantes
	.section .rodata
saludo:	.string "Estructura de Computadores curso 2022. UPNA 28 de Octubre"
resultado: .string

	.data
mensaje:	.byte	0x6D		#109(m) --> 77(M)
Z:	.byte
X:	.byte	0xFE
Y:	.byte 	0xFF

	.global main
	.text
	
main:


	# imprimir saludo
    #    push $saludo
    #    call puts

	# Convertir la letra minúscula en mayúscula
	## mov mensaje, %edx
	## sub $0x20, %edx
	## mov %edx, %ebx 
	###################
	subb $0x20, mensaje
	
	#mov mensaje, %ebx		# devolver valor al S.O.
	# mov $1, %eax		
	# int $0x80

	# Operación multiplicación lógica Z=XY
	mov X, %al
	and Y, %al
	#imul %bl		# AL pasa a contener X*Y=3
	
	jpo false		# si PF=0	
	jpe true		# si PF=1	# me funciona mal pues me dice que siempre es par
	
true:
	movb $1,Z
	mov Z, %ebx		# devolver valor al S.O.
	mov $1, %eax
	int $0x80
	
false:
	movb $0,Z
	mov Z, %ebx		# devolver valor al S.O.
	mov $1, %eax
	int $0x80

	## Imprimir el resultado
	## call puts

	# Pasar el resultado al sistema operativo
	

	# Fin
.end
