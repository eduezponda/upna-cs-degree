#include "arbolBin.h"
#include <stdio.h>
#include <stdlib.h>

void nuevoArbolBin(tipoArbolBin *a)
{
	*a = NULL;
}

bool esVacio(tipoArbolBin a)
{
	return a == NULL;
}

tipoArbolBin construir(tipoElementoArbolBin e, tipoArbolBin a, tipoArbolBin b)
{
	celdaArbolBin* aux;
	
	aux = (celdaArbolBin*)malloc(sizeof(celdaArbolBin));
	aux->elem = e;
	aux->izda = a;
	aux->dcha = b;
	
	return aux;
}

tipoElementoArbolBin devolverRaiz(tipoArbolBin a)
{
	if (!(esVacio(a)))
	{
		return a->elem;
	}
	else
	{
		printf("Arbol nulo\n");
		exit(1);
	}
}

void preorden(tipoArbolBin a)
{
	if (!(esVacio(a)))
	{
		printf(" %d",a->elem);
		preorden(a->izda);
		preorden(a->dcha);
	}
}

void inorden(tipoArbolBin a)
{
	if (!(esVacio(a)))
	{
		inorden(a->izda);
		printf(" %d",a->elem);
		inorden(a->dcha);
	}
}

void postorden(tipoArbolBin a)
{
	if (!(esVacio(a)))
	{
		postorden(a->izda);
		postorden(a->dcha);
		printf(" %d",a->elem);
	}
}
