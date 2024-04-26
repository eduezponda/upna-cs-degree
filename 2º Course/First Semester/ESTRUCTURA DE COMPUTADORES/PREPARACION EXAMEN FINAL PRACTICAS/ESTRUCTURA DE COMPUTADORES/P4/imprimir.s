	.section .data
planet:
	.long 9 # variable planet
	.section .rodata
mensaje:
	.asciz "El número de planetas es %d \n" #string con formato de la función printf
	.global _start
	.section .text
_start:
	## imprimir en la pantalla
	push planet # 2º argumento de la función printf
	push $mensaje # 1º argumento de la función printf: dirección del string
	call printf
	## salir al sistema
	push $0
	call exit
