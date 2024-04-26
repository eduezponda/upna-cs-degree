#include<stdio.h>

#include "colaPrioridades.h"

int main(){
	tipoValorElemento elem;
	tipoCola cola;
	int opcion;
	int tecla;
	bool prioridad;
	
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
				printf("Introduce el valor: ");
				scanf("%d",&elem);
				printf("Indica si es prioritario (pulsar tecla 1): ");
				scanf("%d", &tecla);
				if (tecla == 1){
					prioridad = true;
					printf("elegido como prioritario\n");
				}else{
					prioridad = false;
					printf("elegido como secundario\n");
				}
				encolar(&cola,elem, prioridad);
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
