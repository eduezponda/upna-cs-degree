#include<stdio.h>

#include "colaEnteros.h"

int main(){
	tipoElementoCola elem;
	tipoCola cola;
	int opcion;
	
	nuevaCola(&cola);
	do
    {
		printf("--------MENU-------- \n");
		printf("1 - Encolar un elemento\n");
		printf("2 - Desencolar un elemento\n");
		printf("3 - Mostrar elemento del frente.\n");
		printf("4 - Salir.\n");
		printf("Escoja una opcion: ");
		scanf("%d",&opcion);
		switch(opcion){           
			case 1: 
				printf("Introduce el entero: ");
				scanf("%d",&elem);
				encolar(&cola,elem);
				break;    
			case 2: 
				desencolar(&cola);
				printf("Se ha eliminado un elemento\n");
				break;
			case 3:
				elem=frente(cola);
				printf("El frente es %d\n",elem);
				break;
		}
	}while(opcion<4);
}
