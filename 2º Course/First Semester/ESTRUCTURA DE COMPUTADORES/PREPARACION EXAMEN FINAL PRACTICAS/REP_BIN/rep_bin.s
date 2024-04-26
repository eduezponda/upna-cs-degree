	.section .data

dato_entrada:
	.byte	-127
	
Mensaje_1:
	.string "Examen Enero 2019"
Mensaje_2:
	.string "El número es Positivo :"
Mensaje_3:
	.string "El número es Negativo :"
Mensaje_4:
	.string "El número es Par :"
Mensaje_5:
	.string "El número es Impar :"
	
	.global main
	.section .text

main: 
	push $Mensaje_1
	call puts
	
	movb dato_entrada, %dl
	cmpb $0, %dl
	jg positivo
	jle negativo
	
positivo:
	push $Mensaje_2
	call puts
	jmp funcion

negativo:
	push $Mensaje_3
	call puts
	
	
	
funcion:
	push $Mensaje_4
	push $Mensaje_5
	push dato_entrada
	call subrutina
	
fin:
	mov $1, %eax
	mov $0, %ebx
	int $0x80
	
	
    .type subrutina, @function
    
subrutina:
	pushl %ebp
	movl %esp, %ebp

	movb 8(%ebp), %al
	movsbw %al, %ax
	mov $2, %bl
	idiv %bl
	
	cmpb $1, %ah
	je impar
	cmpb $-1, %ah
	je impar
	
par:
	mov 16(%ebp), %edx
	push %edx
	call puts
	jmp fin_subrutina

impar:
	mov 12(%ebp), %edx
	push %edx
	call puts

fin_subrutina:
	mov $0, %eax
	movl %ebp, %esp
  popl %ebp
	ret
	.end
	
	
	
	
		
	





