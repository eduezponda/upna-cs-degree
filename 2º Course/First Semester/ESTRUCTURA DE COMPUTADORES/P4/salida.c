/*
  Programa: salida.c
  Descripción: Termina el proceso y vuelve al S.O. llamando a la función exit()
*/

#include <stdlib.h>

#define SUCCESS 0

void main (void)
{
  exit(SUCCESS); //sysscall (__NR_exit, 0xFF) #define __NR_exit 1
}
