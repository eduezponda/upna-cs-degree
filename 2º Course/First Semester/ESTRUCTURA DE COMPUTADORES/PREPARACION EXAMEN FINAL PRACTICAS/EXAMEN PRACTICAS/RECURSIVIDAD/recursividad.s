
##Programa: recursividad.s
##Subrutina : funsum
##Comando compilador: gcc -m32 -g -o recursividad recursividad.s
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
  .long 0
# Inicio de la función principal main()
  .section .text
  .global main
main:
# Imprimo el string msg1 con las función puts() de la librería standard de C
  push $msg1
  call puts
# Inicializo el registro del valor de retorno de funsum con el valor cero
  xor %edx, %edx
# Paso el argumento: puntero al string msg1
  push $msg1
# Llamada a la función funsum
  call funsum
# Imprimo el resultado de la suma de todos los caracteres con la función printf() de la librería standard de C
  mov %eax, resul
  push resul
  push $msg2
# Salida de la función principal con la llamada al sistema exit()
  mov $1, %eax
  mov $0, %ebx
  int $0x80
# Inicio de la definición de la subrutina funsum
    .type funsum, @function
    
funsum:

# Prólogo
  pushl %ebp
  movl %esp, %ebp
# Salvo el contexto anterior a la subrutina: registros EBX y ECX #
  push %ebx
  push %ecx
# Creo una variable local de 4 bytes en la PILA moviendo el stack pointer: reserva memoria local para el sumando
  subl $4, %esp
# Capturo el argumento de la subrutina y lo guardo en EBX
  mov 8(%ebp), %ebx
# Compruebo condición final recursividad (caracter final string) del carácter salvado en ECX
  xor %ecx, %ecx
  movb (%ebx), %al
  movsbl %al, %eax
  cmp $0x00, %eax
# Si final recursividad salto a la etiqueta fin
  je fin
# Guardo el sumando (carácter capturado) en la variable local anteriormente creada en la PILA
  mov %eax, %esp
# Actualizo el puntero al caracter del string: incremento puntero EBX
  addl $0x1, %ebx
# Paso el argumento y llamo a la función funsum
  ##push %ebx
  call funsum
# Sumo el sumando más el valor de retorno y lo guardo en EAX como nuevo valor de retorno
  mov %esp, %eax	
fin:
# Muevo el stack pointer para estar preparado para la restauración del contexto anterior a la subrutina
  addl $4, %esp
# Restauro el contexto anterior a la subrutina: registros ECX y EBX ###
  pop %ecx
  pop %ebx
# Epílogo
  movl %ebp, %esp
  popl %ebp
# Retorno
  ret
  .end
