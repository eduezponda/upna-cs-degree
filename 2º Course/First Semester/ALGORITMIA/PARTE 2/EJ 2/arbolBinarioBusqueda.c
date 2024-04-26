#include "arbolBinarioBusqueda.h"
#include <stdio.h>
#include <stdlib.h>

void nuevoArbolBB(tipoArbolBB *a)
{
	*a = NULL;
}

void insertar(tipoArbolBB *a, tipoElementoArbolBusqueda e)
{	
	if (esVacio(*a))
	{
		*a = malloc(sizeof(celdaArbolBusqueda));
		(*a)->elem = e;
		(*a)->dcha = NULL;
		(*a)->izda = NULL;
	}
	else
	{
		if (e == (*a)->elem)
		{
		
		}
		else if (e < (*a)->elem)
		{
			insertar(&(*a)->izda,e);
		}
		else
		{
			insertar(&(*a)->dcha,e);
		}
	}
}

void borrar(tipoArbolBB *a, tipoElementoArbolBusqueda e)
{
	celdaArbolBusqueda* aux,*auxant;

	if (esVacio(*a)) 
	{

	}
	else
	{
		if (e == (*a)->elem)
		{
			if (esVacio((*a)->izda) && (*a)->dcha == NULL) //no tiene hijos   esVacio((*a)->izda) o ((*a)->izda) == NULL
			{
				free(*a);
				*a = NULL;
			}
			else if ((*a)->izda == NULL || (*a)->dcha == NULL) //tiene un hijo
			{
				if ((*a)->izda == NULL)
				{
					aux = (*a)->dcha;
					free(*a);
					*a = aux;                                            
				}
				else
				{
					aux = (*a)->izda;
					free(*a);
					*a = aux;	
					
				}
			}
			else // tiene dos hijos
			{
				
				aux = (*a)->dcha;
				auxant = *a;
				
				while (!esVacio(aux->izda))
				{
					auxant = aux;
					aux = aux->izda;
				}
				if (auxant == *a)
				{
				
				}
				else
				{
					auxant->izda = aux->dcha;
					aux->dcha = (*a)->dcha;
				}
				aux->izda = (*a)->izda;
				celdaArbolBusqueda *aux3 = *a;
				*a = aux;
				free(aux3);
				//colocar sucesor en posicion original
				//eliminar antiguo elemento
			}
		}
		else if (e < (*a)->elem)
		{
			borrar(&(*a)->izda,e);
		}
		else
		{
			borrar(&(*a)->dcha,e);
		}
	}
}
void mostrarPreorden(tipoArbolBB a)
{
	if (a != NULL)
	{
		printf(" %d",a->elem);
		mostrarPreorden(a->izda);
		mostrarPreorden(a->dcha);
	}
}

void mostrarInorden(tipoArbolBB a)
{
	if (a != NULL)
	{
		mostrarInorden(a->izda);
		printf(" %d",a->elem);
		mostrarInorden(a->dcha);
	}
}

void mostrarPostorden(tipoArbolBB a)
{
	if (a != NULL)
	{
		mostrarPostorden(a->izda);
		mostrarPostorden(a->dcha);
		printf(" %d",a->elem);
	}
}


bool esVacio(tipoArbolBB a)
{
	return a == NULL;
}






