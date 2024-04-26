#include "minMonticulo.h"
#include <stdio.h>


int main (void)
{
	int i, x;
	tipoElementoMinMonticulo elem;
	tipoMinMonticulo m;
	char res;
	
	
	do
	{
		printf("Introduce el número de elementos a ordenar:");
		scanf(" %d",&x);
		nuevoMinMonticulo(&m,x);
		
		i = 0;
		printf("Introduce los elementos:");
		
		while (i < x)
		{	
			scanf(" %d",&elem);
			insertarMinMonticulo(&m,elem);
			i = i + 1;
			
		}
		
		printf("\nLos elementos ordenados son:");
		
		while (!esVacio(m))
		{
			printf(" %d",devolverRaiz(m));
			eliminarElemento(&m,devolverRaiz(m));
		}
		
		printf("\n¿Deseas salir del programa? s/S\n");
		scanf(" %c", &res);
		
	
	}while (res != 's' && res != 'S');
	
	return 0;
}


