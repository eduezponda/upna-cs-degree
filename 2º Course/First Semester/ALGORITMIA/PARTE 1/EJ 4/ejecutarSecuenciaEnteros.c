#include<stdio.h>

#include "secuenciaEnteros.h"

int main(){
	tipoElementoPila elem;
	tipoSecuencia sec;
	int opcion;
	
	//la pila izquierda hasta el anterior al punto de interes
	//la pila derecha desde el punto de interés
	nuevaSecuencia(&sec);

	do
    {
		printf("--------MENU-------- \n");
		printf("1 - Insertar un elemento delante del punto de interes\n");
		printf("2 - Insertar un elemento en el punto de interes\n");
		printf("3 - Eliminar un elemento del punto de interes\n");
		printf("4 - Consultar el elemento en el punto de interes.\n");
		printf("5 - Avanzar un elemento el punto de interes\n");
		printf("6 - Mover el punto de interes al principio\n");
		printf("7 - Esta el punto al final?\n");
		printf("8 - Esta vacia la secuencia?\n");
		printf("9 - Salir\n");
		printf("Escoja una opcion: ");
		scanf("%d",&opcion);
		switch(opcion){ 
			case 1: 
				printf("Introduce el entero: ");
				scanf("%d",&elem);
				insertarDelantePunto(&sec,elem);
				break;
			case 2: 
				printf("Introduce el entero: ");
				scanf("%d",&elem);
				insertarEnPunto(&sec,elem);
				break;    
			case 3: 
				eliminarEnPunto(&sec);
				printf("Se ha eliminado un elemento\n");
				break;
			case 4:
				elem=consultarEnPunto(sec);
				printf("El elemento en el punto de interes es %d\n",elem);
				break;
			case 5:
				avanzarPunto(&sec);
				break;
			case 6:
				moverPuntoAlPrincipio(&sec);
				break;
			case 7:
				if(esPuntoUltimo(sec))
					printf("El punto de interes esta en el ultimo elemento de la secuencia\n");
				else
					printf("El punto de interes no esta al final de la secuencia\n");
				break;
			case 8:
				if(esVaciaSecuencia(sec))
					printf("La secuencia esta vacia\n");
				else
					printf("La secuencia no esta vacia\n");
				break;
		}
	}while(opcion<9);
}
