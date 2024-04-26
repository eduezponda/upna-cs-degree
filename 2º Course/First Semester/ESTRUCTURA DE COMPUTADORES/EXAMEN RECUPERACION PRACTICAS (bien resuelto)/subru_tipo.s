# SECCION DE DATOS

	.section .data
	
msg1:	.string "Universidad Publica de Navarra\n"
msg2:	.string "Minuscula"
msg3:	.string "Mayuscula"
msg4:	.string "Espacio"
msg5:	.string "Salto de línea"
msg6:	.string "Fin de la cadena"
	
# SECCION DE INSTRUCCIONES

	.global main
	.section .text
main:
	pushl $msg6
	pushl $msg5 
	pushl $msg4
	pushl $msg3
	pushl $msg2
	pushl $msg1
	call tipo_carac

fin:
	movl $1, %eax
	movl $0, %ebx
	int $0x80

# SUBRUTINA TIPO_CARAC	

	.type tipo_carac, @function

tipo_carac:

# PROLOGO
	
	pushl %ebp
	movl %esp, %ebp
	
#################################################
### SALVO EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	pushl %eax
	pushl %ebx
	pushl %ecx
	pushl %edx
	pushl %edi
	pushl %esi
#################################################

# CAPTURAR ARG

	mov 8(%ebp), %ebx # EBX = MSG1

  	xor %ecx, %ecx
 	xor %edx, %edx
 	
bucle:
# Compruebo el final del bucle
	movb (%ebx), %al
  	cmpb $0x00, %al
  	je fin_subrutina
	cmpb $0x20, %al
	je espacio
	cmpb $0x0d, %al
	je salto
	cmpb $0x5a, %al
	jle mayuscula
	cmpb $0x61, %al
	jge minuscula
# Cuerpo del bucle
continuar:
  	inc %ebx
# fin del bucle
  	jmp bucle

espacio:
	mov 20(%ebp), %eax
	push %eax
	call puts
	jmp continuar
salto:
	mov 24(%ebp), %eax
	push %eax
	call puts
	jmp continuar
mayuscula:
	cmpb $0x41, %al
	jl continuar
	mov 16(%ebp), %eax
	push %eax
	call puts
	jmp continuar
minuscula:
	cmpb $0x7a, %al
	jge continuar
	mov 12(%ebp), %eax
	push %eax
	call puts
	jmp continuar

fin_subrutina:	
	mov 28(%ebp), %eax
	push %eax
	call puts
	#################################################
### RESTAURO  EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	pop %esi
	pop %edi
	pop %edx
	pop %ecx
	pop %ebx
	pop %eax
#################################################
	##Epílogo 
	
	mov $0, %eax
	movl %ebp, %esp
  	popl %ebp
	ret

	.end
