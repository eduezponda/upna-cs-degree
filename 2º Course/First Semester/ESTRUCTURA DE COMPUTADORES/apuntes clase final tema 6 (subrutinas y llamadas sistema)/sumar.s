  	.section .rodata
  	
saludo: .string "Estructura de Computadores 2020"
  
    	.section .text
    	.global main

main:
	push %ebp
	mov %esp, %ebp
	
	push $saludo ##añadir direccion a pila
	call puts

	mov $4, %eax
	mov $1,%ebx    ##write(FD ($1),puntero string ($saludo), tamaño bytes(string tiene 31 letras))
	mov $saludo,%ecx
	mov $31,%edx
	int $0x80
	
	push %ebp  ##prologo
    	mov %esp, %ebp ##prologo

	push $5
	call restar
	
	push $-1
	call exit

    	mov $1, %eax ##si eliminas estos dos comandos ->violacion core (entrar posicion memoria que no tienes acceso) -> no retorna valor funcion restar
    	int $0x80
    
    	mov %ebp, %esp  #epilogo
    	pop %ebp #epilogo
    	ret
    
    	.type restar,@function
restar:
   	 push %ebp
   	 mov %esp, %ebp
    
    	mov %ebp, %esp
    	pop %ebp
    	ret	
