/*
	Programa: pixel_linea.s
	Nombre: Fermin Sola Bienzobas
	Fecha: 10/01/2022
*/
	
	## Sección Macros
	.equ DIMENSION_Z, 3		#tamaño de la tercera dimensión: estructura pixel

	.section .text
	
	.global pixels_generator_linea
	.type pintar, @function
	.type pixels_generator_linea, @function
	
pintar:
	push    %ebp
    mov     %esp, %ebp
    
    # posicion agumentos dentro de la pila:
    #     dimension --> 32+EBP
    #     reg_mem --> 28+EBP
    #     red --> 24+EBP
    #     green --> 20+EBP
    #     blue --> 16+EBP
    #     j --> 12+EBP
    #     i --> 8+EBP
    
    mov     8(%ebp), %eax
    imul    32(%ebp), %eax
    add     12(%ebp), %eax
    imul	$DIMENSION_Z, %eax
    mov     28(%ebp), %ecx
    add     %eax, %ecx
    mov    16(%ebp), %eax
    mov    %eax, (%ecx)
    mov    20(%ebp), %eax
    mov    %eax, 1(%ecx)
    mov    24(%ebp), %eax
    mov    %eax, 2(%ecx)
    
    ################# cambiar y entender funcion pintar
    
    pop     %ebp
    ret


pixels_generator_linea:
	## Nuevo frame en la pila
	push %ebp
	mov %esp, %ebp

	# posicion agumentos dentro de la pila:
    
    #	reg_mem --> EBP+28
    #	dimension --> EBP+24
    #	y2 --> EBP+20
    #	x2 --> EBP+16
    #	y1 --> EBP+12
    #	x1 --> EBP+8
    ##	ret --> EBP+4
    ##	EBP --> EBP+0
    
    sub  $32, %esp		# reservo espacio para las variables locales en la pila

    #	i1 --> EBP-4
    #	i2 --> EBP-8
    #	dx --> EBP-12
    #	dy --> EBP-16
    #	d --> EBP-20
    #	y --> EBP-24 
    #	x --> EBP-28
    #	xmax --> EBP-32
    
    #################################################
### SALVO EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	push %eax
	push %ebx
	push %ecx
	push %edx
	push %edi
	push %esi
#################################################
    
	
	## dx=x2-x1;
	mov 16(%ebp), %eax
	sub 8(%ebp), %eax		# eax = x2 - x1
	mov %eax, -12(%ebp)
	
	## dy = y2-y1;
	mov 20(%ebp), %eax
	sub 12(%ebp), %eax		# eax = y2 - y1
	mov %eax, -16(%ebp)
	
	## i1=2*dy;
	mov -16(%ebp), %eax
	imul $2, %eax			# eax = 2 * dy
	mov %eax, -4(%ebp)
	
	
	## i2=2*(dy-dx);
	mov -16(%ebp), %eax
	sub -12(%ebp), %eax		# eax = dy - dx
	imul $2, %eax			# eax = 2 * (dy-dx)
	mov %eax, -8(%ebp)	
 
	cmpw $0, -12(%ebp)	# dx - 0
	jns	NoNegativo1		# salta si SF=0 (si NO es negativo)
	
	## if (dx<0)
	## 	x=x2;
	mov 16(%ebp), %eax
	mov %eax, -28(%ebp)
	
	## 	y=y2;
	mov 20(%ebp), %eax
	mov %eax, -24(%ebp)
	
	## 	xmax=x1;
	mov 8(%ebp), %eax
	mov %eax, -32(%ebp)
	
	jmp salto1
	
	## else
NoNegativo1:
	
	## 	x=x1;
	mov 8(%ebp), %eax
	mov %eax, -28(%ebp)
	
	## 	y=y1;
	mov 12(%ebp), %eax
	mov %eax, -24(%ebp)
	
	## 	xmax=x2;
	mov 16(%ebp), %eax
	mov %eax, -32(%ebp)

salto1: 
	 
	## d=2*dy-dx; 
	mov -16(%ebp), %eax
	imul $2, %eax		# eax = 2 * dy
	sub -12(%ebp), %eax		# eax = (2*dy) - dx
	mov %eax, -20(%ebp)
	
	## while (x < xmax){
	mov -32(%ebp), %eax
	cmpl %eax, -28(%ebp)		# x - xmax
	js bucle				# salta al bucle si la resta es negativa (SF=1)

bucle:
	## 	if (d<0)
	cmpw $0, -20(%ebp)
	jns NoNegativo2		# salta si d es positivo
	
	## 	d=d+i1;
	mov -20(%ebp), %eax
	add -4(%ebp), %eax		# eax = d + i1
	mov %eax, -20(%ebp)
	
	jmp salto2
	
	## 	else
NoNegativo2:

	## 	d=d+i2;
	mov -20(%ebp), %eax
	add -8(%ebp), %eax
	mov %eax, -20(%ebp)
	
	## 	y=y+1;
	mov -24(%ebp), %eax
	incl %eax
	mov %eax, -24(%ebp)
	
salto2:
	## 	x=x+1;
	mov -28(%ebp), %eax
	incl %eax
	mov %eax, -28(%ebp)
	
	## 	// x columnas -> j
	## 	// y filas -> i
	## 	pintar(y,x,0,0,0xFF,reg_mem);
	push 24(%ebp)
	push 28(%ebp)
	push $0xFF
	push $0
	push $0
	push -28(%ebp)
	push -24(%ebp)
	
	call pintar
	
	add $28, %esp
	
	mov -32(%ebp), %eax
	cmpl %eax, -28(%ebp)	# x - xmax
	js bucle				# salta al bucle si la resta es negativa (SF=1)
	
	
	#################################################
### RESTAURO  EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	pop %esi
	pop %edi
	pop %edx
	pop %ecx
	pop %ebx
	pop %eax
#################################################
	
	## Recuperar el antiguo frame
	# add $32, %esp		# salto las variables locales (no hace falta por la siguiente instruccion)
	
	mov %ebp,%esp
	pop %ebp
	
	ret

	.end
