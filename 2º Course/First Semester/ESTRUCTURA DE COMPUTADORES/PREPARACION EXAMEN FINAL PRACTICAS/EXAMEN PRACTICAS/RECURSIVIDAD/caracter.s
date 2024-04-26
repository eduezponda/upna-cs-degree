	.section .rodata
	
msg1:	.string "Universidad Publica de Navarra UPNA"
msg2:	.string "resultado: 0x%lx\n"
resul:	.long 0
    
    .section .text
    .global main

main:
	push $msg1
	call puts

	push $msg1
	call caracter
	
	pushl %eax	# EAX contiene el valor devuelto por subrutina caracter
	pushl $msg2
	call printf
	
	mov $1, %eax
	mov $0, %ebx
	int $0x80
	
	.type caracter, @function
	
caracter:
   # PROLOGO
	
	pushl %ebp
	movl %esp, %ebp
	
	pushl %ebx
	pushl %ecx
	
   # CUERPO
	
	subl $4, %esp
	movl 8(%ebp), %ebx	# EBX contiene la direccion de msg1
	
  	movb 10(%ebx), %al
  	movsbl %al, %eax
	
   # EPILOGO
	
	addl $4, %esp
	
	pop %ecx
  	pop %ebx
  	
  	movl %ebp, %esp
  	popl %ebp
  	
  	ret
  	
  	.end

