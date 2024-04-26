/*
Programa syscall_write_puts.c
Descripción: Realiza la llamada al sistema operativo para imprimir en la
pantalla
Realiza la llamada de tres formas diferentes: puts,write,syscall.
Compilación: gcc -m32 -g -o puts_gets puts_gets.c
*/
// Cabeceras de librerías
#include <stdio.h> // prototipo de la función puts()
#include <unistd.h> //declaración de las macros STDOUT_FILENO, STDIN_FILENO
#include <syscall.h> //declaración de la función syscall
#include <sys/syscall.h> // declaración de la macro __NR_write y __NR_exit
#include <stdlib.h> //declaración de exit()
// Macros
#define LON_BUF 5 // Tamaño del string
void main (void)
{
	char buffer[LON_BUF]="Hola\n";
	puts("\n***************** Práctica : LLAMADAS AL SISTEMA *******************\n"); // función puts() de la librería libc
	puts("\n***************** Imprimo el mensaje de bienvenida mediante la función write(): ");
	write(STDOUT_FILENO, buffer,LON_BUF); // wrapper de la llamada al sistema write. 
	// ya que write() incluye un syscall(), llama indirectamente al sistema
	puts("\n***************** Imprimo el mensaje de bienvenida mediante la llamada al sistema syscall(): ");
	syscall(__NR_write,STDOUT_FILENO,buffer,LON_BUF); // función syscall de llamada directa al sistema.
	exit(0xAA); //Salir al sistema envíando el código 0xAA. No es lo mismo que retornar.
}
