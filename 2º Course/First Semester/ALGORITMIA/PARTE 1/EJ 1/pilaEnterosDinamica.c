#include "pilaEnterosDinamica.h"

#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

void nuevaPila(tipoPila* p){
	*p = NULL;
}

bool esNulaPila(tipoPila p){
	return p == NULL;
}

void apilar (tipoPila* p , tipoElementoPila e){
	celdaPila* nuevo;
	nuevo = (celdaPila*)malloc(sizeof(celdaPila));
	nuevo->elem = e;
	nuevo->sig = *p;
	*p = nuevo;
}

void desapilar (tipoPila* p){
	if (esNulaPila(*p)){
		printf("\nERROR: No se puede desapilar de pila NULA\n");
	}else{
		celdaPila* aux;
		aux = *p;
		*p = (*p)->sig;
		free(aux);
	}
}

tipoElementoPila cima(tipoPila p){
	if (esNulaPila(p)){
		printf("\nERROR: No existe cima de pila NULA\n");
	}else{
		return p->elem;
	}
}
