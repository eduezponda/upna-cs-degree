/*Programa: sumMtoN_aviso.c
## Descripción: realiza la suma de números enteros de la serie M,M+1,M+2,M+3,...N función : sumMtoN(1o arg=M, 2o arg=N) donde M < N
## Ejecución: Editar los valores M y N y compilar el programa.
##	Ejecutar $./sumMtoN_aviso
##	El resultado de la suma se captura del sistema operativo con el comando linux: echo $?

##	gcc -m32 -g -o sumMtoN_aviso sumMtoN_aviso.c
##	Ensamblaje as --32 --gstabs sumMtoN_aviso.c -o sumMtoN_aviso.o
##	linker -> ld -melf_i386 -o sumMtoN_aviso sumMtoN_aviso.c
*/

#include <stdio.h>
#include <stdlib.h>

int sumMtoN (int m, int n);

int main (void)
{
	int m, n, ebx;
	char* mensaje = "ERROR, se debe de cumplir la relación M < N\n";
	
	m = 5;
	n = 10;
	
	if (m < n)
	{
		ebx = sumMtoN (m, n);
	}
	else
	{
		printf("%s",mensaje);
		ebx = 0; //realmente no haría falta esto ya que si entra a la función sumMtoN no daría ninguna vuelta en el bucle y el resultado sería 0
	}
	exit(ebx);
}
int sumMtoN (int m, int n)
{
	int suma = 0;
	
	while (n - m >= 0)
	{
		suma = suma + m;
		m = m + 1;
	}
	
	return suma;
}
