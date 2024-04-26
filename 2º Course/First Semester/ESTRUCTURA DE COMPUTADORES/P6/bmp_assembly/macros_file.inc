	.equ DIMENSION_Z, 3		#tamaño de la tercera dimensión: estructura pixel

	.macro PROLOGO
	  push %ebp
	  mov %esp, %ebp
	.endm

	.macro EPILOGO
	  mov %ebp,%esp
	  pop %ebp
	.endm

	.macro SALVAR_CONTEXTO
	  push %eax
	  push %ebx
	  push %ecx
	  push %edx
	  push %edi
	  push %esi
	.endm

	.macro RECU_CONTEXTO
	  pop %esi
	  pop %edi
	  pop %edx
	  pop %ecx
	  pop %ebx
	  pop %eax
	.endm

	.macro CAPTURAR_ARGUMENTOS
	## Capturo el argumento origen_x
	mov 8(%ebp),%eax
	mov %eax,origen_x
	## Capturo el argumento origen_y
	mov 12(%ebp),%eax
	mov %eax,origen_y
	## Capturo el argumento lado
	mov 16(%ebp),%eax
	mov %eax,lado
	## Capturo el argumento proporcion
	mov 20(%ebp),%eax
	mov %eax,proporcion
	## Capturo el argumento dimension
	mov 24(%ebp),%eax
	mov %eax,dimension
	## Capturo el argumento buffer
	mov 28(%ebp),%eax
	mov %eax,buffer_ptr   #inicializo el puntero con el argumento
	.endm

	.macro INICIAR_COOR_LIMITES
	mov origen_x,%eax
	add lado,%eax
	dec %eax
	mov %eax,fin_x
	mov origen_y,%eax
	add lado,%eax
	dec %eax
	mov %eax,fin_y
	.endm

	.macro INICIAR_COLOR
	## intensidad colores: color INICIAL
	## color background ubuntu RGB is (48, 10, 36).
	movb $0xff,rojo
	movb $0x00,verde
	movb $0x00,azul
	.endm

	.macro PX_POSICION_BUFFER
	## Reset registros aritmética
	xor %eax,%eax
	xor %ebx,%ebx
	xor %ecx,%ecx
	## Aritmética Fila
	movw %di,%bx		#posición fila
	imul dimension,%ebx
	imul $DIMENSION_Z,%ebx
	## Aritmética Columna
	movw %si,%cx		#posición columna
	imul $DIMENSION_Z,%ecx
	## Correspondencia array_pixel -> posición buffer
	xor %eax,%eax
	add %ebx,%eax
	add %ecx,%eax
	mov %eax,%ebx    #EBX contiene el offset del pixel en el buffer
	.endm

	.macro PX_COLOR
	## Actualizar colores en el pixel
	## parámetro de entrada EDI: fila
	## parámetro de entrada ESI: columna
	xor %ecx,%ecx		#índice color
	movb azul,%al		#intensidad azul
	xor %edx,%edx		#registro division
	mov  %edi,%eax
	imul $0xFF,%eax
	div dimension,%eax
        mov buffer_ptr,%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	inc %ecx
	movb verde,%al		#intensidad verde
	xor %edx,%edx		#registro division
	mov  %edi,%eax
	imul $0xFF,%eax
	div dimension,%eax
	mov buffer_ptr,%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	inc %ecx
	movb rojo,%al		#intensidad rojo
	xor %edx,%edx		#registro division
	mov  %esi,%eax
	imul $0xFF,%eax
	div dimension,%eax
	sub  $0xFF,%eax
	mov buffer_ptr,%edx
	lea (%edx,%ebx),%edx  
	mov %eax,(%edx,%ecx) # dirección efectiva = M[buffer_ptr] + offset + posi_color
	.endm

