#include<stdio.h>
#include "minMonticulo.h"

int main(){
	int numEl,opcion;
	tipoElementoMinMonticulo elem;
	tipoMinMonticulo mon;
	printf("Introduce el numero maximo de elementos: ");
	scanf("%d",&numEl);
	nuevoMinMonticulo(&mon,numEl);
	do
    {
		printf("--------MENU-------- \n");
		printf("1 - Insertar un elemento en el monticulo\n");
		printf("2 - Eliminar un elemento del monticulo\n");
		printf("3 - Mostrar el elemento de la raiz\n");
		printf("4 - Mostrar el monticulo en anchura\n");
		printf("5 - Salir.\n");
		printf("Escoja una opcion: ");
		scanf("%d",&opcion);
		switch(opcion){           
			case 1: 
				printf("Introduce el entero: ");
				scanf("%d",&elem);
				insertarMinMonticulo(&mon,elem);
				break;
			case 2: 
				printf("Introduce el entero: ");
				scanf("%d",&elem);
				eliminarElemento(&mon,elem);
				break;   
			case 3:
				printf("El elemento de la raiz es %d\n",devolverRaiz(mon));
				break;
			case 4: 
				printf("Los elementos del monticulo son:\n");
				mostrarAnchura(mon);
				printf("\n");
				break;
		}
	}while(opcion<5);
}