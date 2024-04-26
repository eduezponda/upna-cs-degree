## Programa: sumMtoN_aviso.s
## Descripción: realiza la suma de números enteros de la serie M,M+1,M+2,M+3,...N función : sumMtoN(1o arg=M, 2o arg=N) donde M < N
## Ejecución: Editar los valores M y N y compilar el programa.
##	Ejecutar $./sumMtoN_aviso
##	El resultado de la suma se captura del sistema operativo con el comando linux: echo $?

##	gcc -nostartfiles -m32 -g -o sumMtoN_aviso sumMtoN_aviso.s
##	Ensamblaje as --32 --gstabs sumMtoN_aviso.s -o sumMtoN_aviso.o
##	linker -> ld -melf_i386 -o sumMtoN_aviso sumMtoN_aviso.o

	## MACROS
	.equ SYS_EXIT, 1
	## DATOS
	.section .data
n:
	.int 10
m:
	.int 5
mensaje:
	.asciz "ERROR, se debe de cumplir la relación M < N"
	## INSTRUCCIONES
	.section .text
	.global _start
_start:
	mov n, %eax
	cmp m, %eax #compara realizando un sub -> eax - m

	jl string

	## Paso los dos argumentos M y N a la subrutina a través de la pila
	pushl n	#push second argument -> N ->no apilar dirección sino contenido
	pushl m	#push first argument -> M ->no apilar dirección sino contenido

	## Llamada a la subrutina sum1toN
	call sumMtoN
	jmp fin
	## Paso la salida de sum11toN al argumento a la llamada al sistema exit()
string:
	push $mensaje
	call puts
	mov $0, %eax  #paso el valor de 0 al registro eax porque luego le va a pasar ese valor a ebx
fin:
	mov %eax, %ebx # ( %ebx is returned)
	## Código de la llamada al sistema operativo
	movl $SYS_EXIT, %eax # llamada exit
	## Interrumpo al S.O.
	int $0x80

##Subrutina: sumMtoN
##Descripción: calcula la suma de números enteros en secuencia desde el 1o sumando hasta el 2o sumando
##	Argumentos de entrada: 1o sumando y 2o sumando los argumentos los pasa la rutina principal a través de la pila:
##	1o se apila el último argumento y finalmente se apila el 1o argumento.
##	Argumento de salida: es el resultado de la suma y se pasa a la rutina principal a través del registro EAX.
##	Variables locales: se implementa una variable local en la pila pero no se utiliza
	
	.type sumMtoN, @function # declara la etiqueta sumMtoN

sumMtoN:
	## Próĺogo: Crea el nuevo frame del stack
	pushl %ebp #salvar el frame pointer antiguo
	movl %esp, %ebp #actualizar el frame pointer nuevo
	## Reserva una palabra en la pila como variable local
	## Variable local en memoria externa: suma
	subl $4, %esp
	## Captura de argumentos
	movl 8( %ebp), %ebx #1o argumento copiado en %ebx
	movl 12( %ebp), %ecx #2o argumento copiado en %ecx

	## suma la secuencia entre el valor del 1oarg y el valor del 2oarg
	## 1o arg < 2oarg
	## utilizo como variable local EDX en lugar de la reserva externa para variable local: optimiza velocidad
	## Inicializo la variable local suma
	movl $0, %edx

	## Número de iteracciones
	mov %ecx, %eax
	sub %ebx, %eax

bucle:
	add %ebx, %edx
	inc %ebx
	sub $1, %eax
	jns bucle

	## Salvo el resultado de la suma como el valor de retorno
	movl %edx, %eax

	## Epílogo: Recupera el frame antiguo
	movl %ebp, %esp #restauro el stack pointer
	popl %ebp #restauro el frame pointer

	## Retorno a la rutina principal
	ret
	.end
