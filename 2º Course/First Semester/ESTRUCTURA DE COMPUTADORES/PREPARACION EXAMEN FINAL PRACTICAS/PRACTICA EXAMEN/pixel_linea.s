	.section .data
	
	i1:	.4byte 0
	i2:	.4byte 0
	dx:	.4byte 0
	dy:	.4byte 0
	d:	.4byte 0
	x:	.4byte 0
	y:	.4byte 0
	xmax:	.4byte 0
	origen_x: .4byte 0
	origen_y: .4byte 0
	lado: .4byte 0
	proporcion: .4byte 0
	dimension: .4byte 0
	buffer:  	.4byte  0
	
	.section .text
	.global pixels_generator_linea
	
	.type pixels_generator_linea, @function
	.type pintar, @function
	
pixels_generator_linea:
	## Nuevo frame en la pila
	push %ebp
	mov %esp, %ebp
#################################################
### SALVO EL CONTEXTO ANTERIOR A LA SUBRUTINA ###
	push %eax
	push %ebx
	push %ecx
	push %edx
	push %edi
	push %esi
	
################################################# 
	mov 8(%ebp),%eax
	mov %eax, origen_x
	
	mov 12(%ebp),%eax
	mov %eax, origen_y
	
	mov 16(%ebp),%eax
	mov %eax, lado
	
	mov 20(%ebp),%eax
	mov %eax, proporcion
	
	mov 24(%ebp),%eax
	mov %eax, dimension
	
	mov 28(%ebp),%eax
	mov %eax, buffer
	
	mov lado, %eax
	sub origen_x, %eax
	mov %eax, dx
	
	mov proporcion, %eax
	sub origen_y, %eax
	mov %eax, dy
	
	mov dy, %eax
	imul $2, %eax
	mov %eax, i1
	
	mov dy, %eax
	sub dx, %eax
	imul $2, %eax
	mov %eax, i2
	
	mov dx, %eax
	cmp $0, %eax
	jl if
	jge else
	
if:
	mov lado, %eax
	mov %eax, x
	mov proporcion, %eax
	mov %eax, y
	mov origen_x, %eax
	mov %eax, xmax
	jmp fin_if
	
else:
	mov origen_x, %eax
	mov %eax, x
	mov origen_y, %eax
	mov %eax, y
	mov lado, %eax
	mov %eax, xmax

fin_if:
	mov dy, %eax
	imul $2, %eax
	sub dx, %eax
	mov %eax, d
	
while:
	mov xmax, %eax
	cmp x, %eax
	jle fin
	
	mov d, %eax
	cmp $0, %eax
	jl if2
	jge else2
	
if2:
	mov d, %eax
	add i1, %eax
	mov %eax, d
	jmp contador

else2:
	mov d, %eax
	add i2, %eax
	mov %eax, d
	
	incl y
	
contador:
	incl x
	push buffer
	push $0xff
	push $0x00
	push $0x00
	push x
	push y
	call pintar
	jmp while
	
fin:
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
	mov %ebp,%esp
	pop %ebp
	
	ret
	
pintar: # pintar(i, j, azul, verde, rojo, buffer)
	push %ebp
  mov %esp, %ebp
  
  xor %eax,%eax
	xor %ebx,%ebx
	xor %ecx,%ecx
	## Aritmética Fila
	movw 24(%ebp),%di
	movw %di,%bx		#posición fila
	imul $1024,%ebx
	imul $3,%ebx
	## Aritmética Columna
	movw 28(%ebx),%si
	movw %si,%cx		#posición columna
	imul $3,%ecx
	## Correspondencia array_pixel -> posición buffer
	xor %eax,%eax
	add %ebx,%eax
	add %ecx,%eax
	mov %eax,%ebx     #EBX contiene el offset del pixel en el buffer
	
	## Actualizar colores en el pixel
	xor %ecx,%ecx		#índice color
	movb 20(%ebp),%al		#intensidad azul
  mov 8(%ebp),%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	inc %ecx
	movb 16(%ebp),%al		#intensidad verde
	mov 8(%ebp),%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	inc %ecx
	movb 12(%ebx),%al		#intensidad rojo
	mov 8(%ebp),%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
  
  pop %ebp
  
  ret
  
	.end
