/*#include <stdlib.h>
void main (void)
{
	exit (0xFF);
}
/* Llamada al sistema desde C
Prototipo: int syscall(int number, ...);
man syscall
*/
#define _GNU_SOURCE
#include <unistd.h>
#include <sys/syscall.h>
void main (void)
{
	syscall (__NR_exit,0xFF);
}

//compilar: gcc -m32 -g -o salida0 salida0.c
// en ambos programas te devuelve el 255, simplemente son formas distintas del exit
