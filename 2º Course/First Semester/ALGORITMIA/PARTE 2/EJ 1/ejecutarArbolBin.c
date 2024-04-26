#include<stdio.h>
#include "arbolBin.h"

int main(){
	tipoElementoArbolBin elem;
	tipoArbolBin arbol1,arbol2;
	int i,num;
	
	nuevoArbolBin(&arbol1);
	nuevoArbolBin(&arbol2);
	
	printf("Introduce 7 numeros para crear el arbol: ");
	scanf("%d",&num);
	arbol1=construir(num,NULL,NULL);
	for(i=1;i<=3;i++){
		scanf("%d",&num);
		arbol2=construir(num,NULL,NULL);
		scanf("%d",&num);
		arbol1=construir(num,arbol1,arbol2);
	}
	printf("El elemento de la raiz es %d\n",devolverRaiz(arbol1));
	printf("El arbol es preorden es: ");
	preorden(arbol1);
	printf("\nEl arbol es inorden es: ");
	inorden(arbol1);
	printf("\nEl arbol es postorden es: ");
	postorden(arbol1);
}