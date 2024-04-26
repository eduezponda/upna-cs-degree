### Programa: sum1toN.s
### Descripción: realiza la suma de la serie 1,2,3,...N. La entrada se define en el propio programa y la salida se pasa al S.O.
### Lenguaje: Lenguaje ensamblador de GNU para la arquitectura i386 -> GNU as ->gas -> AT&T
### Es el programa en lenguaje AT&T i386 equivalente a sum.ias de la máquina IAS de von Neumann
### gcc -m32 -g -nostartfiles -o sum1toN sum1toN.s
### Ensamblaje as --32 --gstabs sum1toN.s -o sum1toN.o
### linker -> ld -melf_i386 -o sum1toN sum1toN.o
## Declaración de variables
## SECCION DE DATOS
	.section .data
n: 	.word 'm'
	.global _start
## Comienzo del código
## SECCION DE INSTRUCCIONES
	.section .text
_start:
	mov $0,%ecx # ECX implementa la variable suma. Mueve el valor 0 al registro c. (edx,ecx en 32 bits, rdx en 64)
	mov n,%edx  #implementa n al registro edx. fuente, destino
bucle:
	addw %dx,%cx	#add %edx,%ecx ecx = ecx + edx   acumulador registro c
	sub $1,%edx   #edx = edx - 1
	jnz bucle     #jnz=salta si el acumulador(edx)>0
	
	mov %ecx, %ebx # pasar el argumento de salida al S.O. a través de EBX según convenio ABI i386. Por convención ->registro b
## salida
	mov $1, %eax # código de la llamada al sistema operativo: subrutina exit
	int $0x80 # llamada al sistema operativo para que ejecute la subrutina según el valor de EAX
	.end
