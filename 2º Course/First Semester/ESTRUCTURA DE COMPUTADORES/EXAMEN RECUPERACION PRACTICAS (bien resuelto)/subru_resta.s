
	.section .data
msg1:
	.string "Estructura de Computadores"
A:
	.int 5
B:
	.int 10
C: 
	.string "Caracter: %c \n"
msg2:
	.string "\nResultado: %d \n"

	.global main
	.section .text

main:
	push $msg1
	call printf

	push B
	push A
	call resta
	
	push %eax
	push $msg2
	call printf
	
	mov $0, %eax
	
	mov $C, %ebx
	push (%eax, %ebx)
	push $C
	call printf
	
fin:
	mov $1, %eax
	mov $0, %ebx
	int $0x80

	.type resta, @function

resta:
	##Prólogo
	pushl %ebp
	movl %esp, %ebp
	
#################################################
### SALVO EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	push %ebx
	push %ecx
	push %edx
	push %edi
	push %esi
#################################################

	##Capturo argumentos

	mov 8(%ebp), %eax ##A
	mov 12(%ebp), %ebx ##B

	sub %ebx, %eax ## eax = eax - ebx; A = A - B

#################################################
### RESTAURO  EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	pop %esi
	pop %edi
	pop %edx
	pop %ecx
	pop %ebx
#################################################
	
	##Epílogo (el valor de retorno ya está en el registro eax)

	movl %ebp, %esp
  	popl %ebp
	ret

	.end



