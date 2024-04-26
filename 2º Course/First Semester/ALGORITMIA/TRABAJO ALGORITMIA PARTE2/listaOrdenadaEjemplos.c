#include <stdio.h>
#include <stdlib.h>
#include "listaOrdenadaEjemplos.h"

void nuevaLista(tipoLista *l)
{
	l->ini = NULL;
	l->fin = NULL;
}

void insertar(tipoLista *l, tipoElementoLista x)
{
	celdaLista* aux;
	
	aux = (celdaLista*)malloc(sizeof(celdaLista));
	aux->elem = x;
	
	
	if (l->ini == NULL)
	{
		aux->ant = NULL;
		aux->sig = NULL;
		l->ini = aux;
		l->fin = aux;
	}
	else
	{
		if (l->ini->elem.dist >= aux->elem.dist)
		{
			if (l->ini->elem.dist > aux->elem.dist)
			{
				aux->ant = NULL;
				aux->sig = l->ini;
				l->ini->ant = aux;
				l->ini = aux;
			}
		}
		else if (l->fin->elem.dist <= aux->elem.dist)
		{
			if (l->fin->elem.dist < aux->elem.dist)
			{
				aux->ant = l->fin;
				aux->sig = NULL;
				l->fin->sig = aux;
				l->fin = aux;
				
			}
		}
		else
		{
			celdaLista* aux2;
	
			aux2 = l->ini;
	
			while ((aux2 != NULL) && (aux2->elem.dist < x.dist))
			{
				aux2 = aux2->sig;
			}
			if (aux2->elem.dist > x.dist)
			{
				aux->sig = aux2;
				aux->ant = aux2->ant;
				aux2->ant->sig = aux;
				aux2->ant = aux;
			}
		}
			
		
	}
}

void eliminarMenor(tipoLista *l)
{
	celdaLista* aux;
	
	if (l->ini != NULL)
	{
		aux = l->ini;
		l->ini = l->ini->sig;
		if (l->ini == NULL)
		{
			l->fin = NULL;
		}
		else
		{
			l->ini->ant = NULL;
		}
		free(aux);
	}
	else
	{
		printf("No se puede eliminar el menor en una lista nula\n");
	}
}

void eliminarMayor(tipoLista *l)
{
	celdaLista* aux;
	
	if (l->ini != NULL)
	{
		aux = l->fin;
		l->fin = l->fin->ant;
		if (l->fin == NULL)
		{
			l->ini = NULL;
		}
		else
		{
			l->fin->sig = NULL;
		}
		free(aux);
	}
	else
	{
		printf("No se puede eliminar el mayor en una lista nula\n");
	}
}

tipoElementoLista consultarMenor(tipoLista l)
{
	if (l.ini == NULL)
	{
		printf("No se puede consultar el menor en una lista nula\n");
		exit(1);
	}
	else
	{
		return l.ini->elem;
	}
	
}

tipoElementoLista consultarMayor(tipoLista l)
{
	if (l.ini == NULL)
	{
		printf("No se puede consultar el mayor en una lista nula\n");
		exit(1);
	}
	else
	{
		return l.fin->elem;
	}
}

bool esNulaLista(tipoLista l)
{
	return l.ini == NULL;
}

void imprimirLista (tipoLista l)
{
	celdaLista* aux;
	
	aux = l.ini;
	
	while (aux != NULL)
	{
		printf(" %f",aux->elem.dist);
		aux = aux->sig;
	}
	printf("\n");
}
void vaciarLista(tipoLista *l)
{
	while (!esNulaLista(*l))
	{
		eliminarMenor(l);
	}
}
