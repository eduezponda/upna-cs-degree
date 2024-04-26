#include "arbolBinarioBusqueda.h"
#include <stdio.h>
#include <stdlib.h>

void vaciarArbolBB (tipoArbolBB *a);

int main (void)
{
	tipoElementoArbolBusqueda x;
	tipoArbolBB a;
	char res;
	
	nuevoArbolBB(&a);
	
	do
	{
		printf("Introduce elementos para ordenar. 0 para terminar:");
		
		scanf(" %d",&x);
		
		while (x != 0)
		{
			insertar(&a,x);
			scanf(" %d",&x);
		}
		
		printf("\nLos elementos ordenados son:");
		mostrarInorden(a);
		
		printf("\n¿Deseas continuar? s/n\n");
		scanf(" %c", &res);
		
		vaciarArbolBB(&a);
	
	}while (res == 's' || res == 'S');
	
	return 0;
}

void vaciarArbolBB (tipoArbolBB *a)
{
	if (esVacio(*a))
	{
	
	}
	else
	{
		celdaArbolBusqueda *aux;
		celdaArbolBusqueda *aux2;
		aux = *a;
		aux2 = (*a)->dcha;
		*a = (*a)->izda;
		free(aux);
		vaciarArbolBB(a);
		vaciarArbolBB(&aux2);
		
	}
}
