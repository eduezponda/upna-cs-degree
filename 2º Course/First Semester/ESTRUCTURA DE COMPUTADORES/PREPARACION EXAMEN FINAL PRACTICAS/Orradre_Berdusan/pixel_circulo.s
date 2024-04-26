## Programa: circulo.s
## Descripción: Este programa obtiene la imagen en formato BMP de un circulo completo obtenido por octantes de diferentes colores mediante el algoritmo de Bresenham (y simetrías)
## Autor: Joaquín Orradre Berdusan
## Fecha: 12/01/2023
##	gcc -m32 pixel_circulo.s -c -g
##  gcc -m32 bmp_circulo.c pixel_circulo.o -g
##  ./a.out
##  display test.bmp   -> se muestra la imagen bmp del circulo

	
	##MACROS
	.equ DIMENSION, 1024
	
	##SUBRUTINAS
	.global pixels_generator_circulo
	.type octantes, @function
	.type pixels_generator_circulo, @function
	.type pintar, @function
	
	
pixels_generator_circulo:
	pushl %ebp
	mov %esp,%ebp
	pushl %ebx
	subl $12, %esp
	movl $0, -8(%ebp)
	movl 16(%ebp),%eax
	movl %eax,-12(%ebp)
	imull $2,%eax
	movl $3, %ebx
	subl %eax, %ebx
	movl %ebx, -16(%ebp)

	##Llamamos a la subrutina octantes pasandole los argumentos
	push 24(%ebp)
	push -12(%ebp)
	push -8(%ebp)
	push 12(%ebp)
	push 8(%ebp)
	call octantes
	addl $20,%esp
	
while:
	movl -8(%ebp),%eax
	cmpl %eax, -12(%ebp)
	jl endwhile
	testl $0xffffffff,-16(%ebp)
	jge else
	movl $4, %eax
	imul -8(%ebp),%eax
	addl -16(%ebp),%eax
	addl $6,%eax
	movl %eax, -16(%ebp)
	jmp endif
	
else:
	movl $4, %eax
	imul -8(%ebp),%eax
	movl $4, %ebx
	imul -12(%ebp),%ebx
	subl %ebx,%eax
	addl -16(%ebp),%eax
	addl $10,%eax
	movl %eax, -16(%ebp)
	decl -12(%ebp)
	
endif:
	incl -8(%ebp)
	##Llamamos a la subrutina octantes pasandole los argumentos
	push 24(%ebp)
	push -12(%ebp)
	push -8(%ebp)
	push 12(%ebp)
	push 8(%ebp)
	call octantes
	add $20,%esp
	jmp while
	
endwhile:

	movl -4(%ebp), %ebx
	mov %ebp,%esp
	popl %ebp
	ret

octantes:

	pushl %ebp
	mov %esp,%ebp
	
	##Pintamos el primer octante (i,j) de color rojo llamando a la subrutina pintar
	push 24(%ebp)
	push $0xff
	push $0
	push $0
	movl 12(%ebp),%eax 
	add 20(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	add 16(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
	
	##Pintamos el segundo octante (j,i) de color verde llamando a la subrutina pintar
	push 24(%ebp)
	push $0
	push $0xff
	push $0
	movl 12(%ebp),%eax 
	add 16(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	add 20(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp

	##Pintamos el tercer octante (-j,i) de color azul llamando a la subrutina pintar
	push 24(%ebp)
	push $0
	push $0
	push $0xff
	movl 12(%ebp),%eax 
	sub 16(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	add 20(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
	##Pintamos el cuarto octante (-i,j) de color blanco llamando a la subrutina pintar
	push 24(%ebp)
	push $0xff
	push $0xff
	push $0xff
	movl 12(%ebp),%eax 
	sub 20(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	add 16(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
	##Pintamos el quinto octante (-i,-j) de color rojo llamando a la subrutina pintar
	push 24(%ebp)
	push $0xff
	push $0
	push $0
	movl 12(%ebp),%eax 
	sub 20(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	sub 16(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
	##Pintamos el sexto octante (-j,-i) de color verde llamando a la subrutina pintar
	push 24(%ebp)
	push $0
	push $0xff
	push $0
	movl 12(%ebp),%eax 
	sub 16(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	sub 20(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
	##Pintamos el septimo octante (j,-i) de color azul llamando a la subrutina pintar
	push 24(%ebp)
	push $0
	push $0
	push $0xff
	movl 12(%ebp),%eax 
	add 16(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	sub 20(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
	##Pintamos el octavo octante (i,-j) de color blanco llamando a la subrutina pintar
	push 24(%ebp)
	push $0xff
	push $0xff
	push $0xff
	movl 12(%ebp),%eax 
	add 20(%ebp),%eax
	push %eax
	movl 8(%ebp),%eax 
	sub 16(%ebp),%eax
	push %eax
	call pintar
	addl $24,%esp
	
    #Finalizar subrutina
	mov %ebp,%esp
	popl %ebp
	ret

	
pintar:

	pushl %ebp
	mov %esp,%ebp
	mov 8(%ebp),%eax
	imul $DIMENSION, %eax
	imul $3, %eax
	mov 12(%ebp),%ebx
	imul $3, %ebx
	add %ebx,%eax
	mov 28(%ebp),%edx
	add %eax,%edx
	movb 16(%ebp),%al
	movb %al,(%edx)
	movb 20(%ebp),%al
	movb %al,1(%edx)
	movb 24(%ebp),%al
	movb %al,2(%edx)

    #Finalizar subrutina y terminar programa
	mov %ebp,%esp
	pop %ebp
	ret
	.end
	

