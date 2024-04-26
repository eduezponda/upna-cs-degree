	.section .data
mensaje:
	.asciz	"\n***************** Práctica : LLAMADAS AL SISTEMA *******************\n"
mensaje2:
	.asciz "\n***************** Imprimo el mensaje de bienvenida mediante la función write(): "
mensaje3:
	.asciz "\n***************** Imprimo el mensaje de bienvenida mediante la llamada al sistema syscall(): "
hola:
	.asciz "Hola\n"
	
	.global _start
	.section .text


# llamada a la función puts de la librería libc. Es necesrio linkar con libc.
_start:	
#pila <-argumento
#call puts
	push $mensaje
	call puts
	
	push $mensaje2
	call puts
# llamada a la función write de la librería libc. Es necesario linkar con libc.
#pila <- 3º argumento
#pila <- 2º argumento
#pila <- 1º argumento
#call write
	push $5
	push $hola
	push $1
	call write
	
	push $mensaje3
	call puts

# LLamada al sistema operativo para ejecutar la operación write
#EAX<-4
#EBX<-1
#ECX<-etiqueta que apunta al string a imprimir
#EDX<-5
	mov $4, %eax
	mov $1, %ebx
	mov $hola, %ecx
	mov $5, %edx
	int $0x80
#call sistema_operativo
# Llamada al sistema operativo para ejecutar la operación exit
#EAX<-1
#EBX<-0
	mov $1,%eax
	mov $0,%ebx
	int $0x80
	.end
