#include<stdio.h>

#include "pilaEnterosDinamica.h"

int main(){
	tipoElementoPila elem;
	tipoPila pila;
	int opcion;
	
	nuevaPila(&pila);
	do
    {
		printf("--------MENU-------- \n");
		printf("1 - Apilar un elemento\n");
		printf("2 - Desapilar un elemento\n");
		printf("3 - Mostrar elemento de la cima.\n");
		printf("4 - Salir.\n");
		printf("Escoja una opcion: ");
		scanf("%d",&opcion);
		switch(opcion){           
			case 1: 
				printf("Introduce el entero: ");
				scanf("%d",&elem);
				apilar(&pila,elem);
				break;    
			case 2: 
				desapilar(&pila);
				printf("Se ha eliminado un elemento\n");
				break;
			case 3:
				elem=cima(pila);
				printf("La cima es %d\n",elem);
				break;
		}
	}while(opcion<4);
}
