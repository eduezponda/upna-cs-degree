
##Programa: bucle.s
##Subrutina : funsum
##Comando compilador: gcc -m32 -g -o bucle bucle.s
##Nombre: Eduardo 
##Apellidos: Ezponda Igea
##Estructura de Computadores 2022-23
##Universidad Publica de Navarra
  .section .data
# inicializo el string msg1 con la cadena "Universidad Publica de Navarra UPNA"
msg1:
	.string "Universidad Publica de Navarra UPNA"
# inicializo el string msg2 con la cadena "resultado: 0x%lx\n"
msg2:
  .string "resultado: 0x%lx\n"
# reservo memoria para el resultado de la suma
resul:
  .int 0
# Inicio de la función principal main()
  .global main
  .section .text
main:
# Imprimo el string msg1 con las función puts() de la librería standard de C
  push $msg1
  call puts
# Paso el argumento a la subrutina funsum: puntero al string msg1
  push $msg1
# Llamada a la función funsum
  call funsum
# Imprimo el resultado de la suma de todos los caracteres con la función printf() de la librería standard de C
  push %eax
  push $msg2
  call printf
# Salida de la función principal con la llamada al sistema exit()
  mov %eax, %ebx
  mov $1, %eax
  int $0x80
# Inicio de la definición de la subrutina funsum
  .type funsum, @function # declara la etiqueta funsum

funsum:
# Prólogo
  pushl %ebp #salvar el frame pointer antiguo
  movl %esp, %ebp #actualizar el frame pointer nuevo
# Salvo el contexto anterior a la subrutina: registros EBX y ECX #
  push %ebx
  push %ecx
# Creo una variable local de 4 bytes en la PILA moviendo el stack pointer: reserva memoria local para el sumando
  subl $4, %esp
# Capturo el argumento de la subrutina y lo guardo en EBX
  movl 8( %ebp), %ebx #1o argumento copiado en %ebx
# Inicialización del bucle
  xor %ecx, %ecx  #inicialización registro ecx
  xor %edx, %edx
# Comienzo del bucle
bucle:
# Compruebo el final del bucle
	movb (%edx,%ebx), %al
	movsbl %al, %eax
  cmpb $0x00, %al
  je fin
# Cuerpo del bucle
  add %eax, %ecx
  inc %edx
# fin del bucle
  jmp bucle
fin:
	mov %ecx, %eax
# Muevo el stack pointer para estar preparado para la restauración del contexto anterior a la subrutina
	addl $4, %esp
# Restauro el contexto anterior a la subrutina: registros ECX y EBX ###
  pop %ecx
  pop %ebx
# Epílogo
  movl %ebp, %esp #restauro el stack pointer
  popl %ebp #restauro el frame pointer
# Retorno  
  ret
  .end
