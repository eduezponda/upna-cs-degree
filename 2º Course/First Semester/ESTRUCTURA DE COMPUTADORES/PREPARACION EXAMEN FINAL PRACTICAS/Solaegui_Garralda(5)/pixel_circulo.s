	.global pixels_generator_circulo # global para que pueda ser llamada desde bmp_circulo.c
	.type pintar, @function
	.type pixels_generator_circulo,@function 
	pixels_generator_circulo:
	## Creo el nuevo frame
	pushl %ebp
	mov %esp,%ebp
	## Salvo el registro EBX puesto que es el único que voy a utilizar
	## cuya responsabilidad es de la subrutina llamada
	pushl %ebx
	## Reservo varibles locales (8 variables)
	subl $32,%esp
	## ec = 3-2*radio
	movl 16(%ebp),%eax
	imull $2,%eax
	movl $3,%ebx
	subl %eax,%ebx
	movl %ebx,-8(%ebp)
	## xc = radio
	movl 16(%ebp),%eax
	movl %eax,-20(%ebp)
	## yc = 0
	movl $0,-28(%ebp)
	## while (yc<xc)
while:
	## esta xc en eax
	cmpl -28(%ebp),%eax # xc - yc
	jle while_out
	## if (ec < 0)
	testl $0xffffffff,-8(%ebp)
	jge else
	## xn = xc
	movl -20(%ebp),%eax
	movl %eax,-16(%ebp)
	## yn = yc + 1
	movl -28(%ebp),%eax
	incl %eax
	mov %eax, -24(%ebp)
	## en = ec + 2*(3+2*yc)
	movl -28(%ebp),%eax
	imull $2,%eax
	addl $3,%eax
	imull $2,%eax
	addl -8(%ebp),%eax
	movl %eax, -12(%ebp)
	jmp endif
else:
	## xn = xc-1
	movl -20(%ebp),%eax
	decl %eax
	movl %eax, -16(%ebp)
	## yn = yc +1;
	movl -28(%ebp),%eax
	incl %eax
	mov %eax, -24(%ebp)
	## en = ec + 2*(5+2*yc-2*xc)
	movl -20(%ebp),%eax
	imull $2,%eax
	movl $2,%ebx
	imull -28(%ebp),%ebx
	subl %eax,%ebx
	addl $5,%ebx
	imull $2,%ebx
	addl -8(%ebp),%ebx
	movl %ebx,-12(%ebp)
endif:
	## j = origen_x+xc;
	movl -20(%ebp),%eax
	addl 8(%ebp),%eax
	movl %eax,-36(%ebp)
	## i = origen_y+yc;
	movl -28(%ebp),%eax
	addl 12(%ebp),%eax
	movl %eax,-32(%ebp)
	
	## pintar(j,i,0,0,0xff,reg_mem)//primero
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0xff
	pushl $0
	pushl $0
	pushl -32(%ebp)
	pushl -36(%ebp)
	call pintar
	addl $28,%esp
	## pintar(i,j,0,0xff,0,reg_mem)//segundo
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0
	pushl $0xff
	pushl $0
	pushl -36(%ebp)
	pushl -32(%ebp)
	call pintar
	addl $28,%esp

	##pintar(origen_y-yc,j,0xff,0,0,reg_mem);//tercero
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0
	pushl $0
	pushl $0xff
	pushl -36(%ebp)
	movl 12(%ebp),%eax
	subl -28(%ebp),%eax
	pushl %eax
	call pintar
	addl $28,%esp
	##pintar(origen_x-xc,i,0xff,0xff,0xff,reg_mem);//cuarto
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0xff
	pushl $0xff
	pushl $0xff
	pushl -32(%ebp)
	movl 8(%ebp),%eax
	subl -20(%ebp),%eax
	pushl %eax
	call pintar
	addl $28,%esp
	##pintar(origen_x-xc,origen_y-yc,0,0,0xff,reg_mem);//quinto
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0xff
	pushl $0
	pushl $0
	movl 12(%ebp),%eax
	subl -28(%ebp),%eax
	pushl %eax
	movl 8(%ebp),%eax
	subl -20(%ebp),%eax
	pushl %eax
	call pintar
	addl $28,%esp
	##pintar(origen_y-yc,origen_x-xc,0,0xff,0,reg_mem);//sexto
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0
	pushl $0xff
	pushl $0
	movl 8(%ebp),%eax
	subl -20(%ebp),%eax
	pushl %eax
	movl 12(%ebp),%eax
	subl -28(%ebp),%eax
	pushl %eax
	call pintar
	addl $28,%esp
	##pintar(i,origen_x-xc,0xff,0,0,reg_mem);//septimo
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0
	pushl $0
	pushl $0xff
	movl 8(%ebp),%eax
	subl -20(%ebp),%eax
	pushl %eax
	pushl -32(%ebp)
	call pintar
	addl $28,%esp
	## pintar(j,origen_y-yc,0xff,0xff,0xFF,reg_mem);//octavo
	pushl 24(%ebp) #dimension
	pushl 28(%ebp)
	pushl $0xff
	pushl $0xff
	pushl $0xff
	movl 12(%ebp),%eax
	subl -28(%ebp),%eax
	pushl %eax
	pushl -36(%ebp)
	call pintar
	addl $28,%esp
	## ec = en
	movl -12(%ebp),%eax
	movl %eax,-8(%ebp)
	## yc = yn
	movl -24(%ebp),%eax
	movl %eax,-28(%ebp)
	## xc = xn
	movl -16(%ebp),%eax
	movl %eax,-20(%ebp)
	jmp while
while_out:
	## recuperamos registro ebx
	movl -4(%ebp),%ebx
	## recuperamos frame antiguo
	movl %ebp, %esp
	popl %ebp
	ret
	



pintar:
	pushl %ebp
	mov %esp,%ebp
	## Salvo ebx puesto que es el único registro que es responsabilidad
	## de subrutina llamada que voy a utilizar
	push %ebx
	## los argumentos los cojo de la pila
	#fila
	mov 8(%ebp),%ebx
	imul 32(%ebp),%ebx
	imul $3, %ebx
	#columna
	mov 12(%ebp),%ecx
	imul $3, %ecx
	#offset en buffer
	addl %ecx,%ebx
	addl 28(%ebp),%ebx
	movl $0,%ecx
	movb 16(%ebp),%al
	movb %al,(%ebx,%ecx)
	incl %ecx
	movb 20(%ebp),%al
	movb %al,(%ebx,%ecx)
	incl %ecx
	movb 24(%ebp),%al
	movb %al,(%ebx,%ecx)

	pop %ebx
	mov %ebp,%esp
	pop %ebp
	ret

	.end
	

