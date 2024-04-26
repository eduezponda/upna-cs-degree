#include <stdio.h>
#include <stdlib.h>
#include "listaOrdenadaEnteros.h"

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
		if (l->ini->elem >= aux->elem)
		{
			if (l->ini->elem > aux->elem)
			{
				aux->ant = NULL;
				aux->sig = l->ini;
				l->ini->ant = aux;
				l->ini = aux;
			}
		}
		else if (l->fin->elem <= aux->elem)
		{
			if (l->fin->elem < aux->elem)
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
	
			while ((aux2 != NULL) && (aux2->elem < x))
			{
				aux2 = aux2->sig;
			}
			if (aux2->elem > x)
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

bool estaElemento(tipoLista l, tipoElementoLista x)
{
	bool b;
	celdaLista* aux;
	
	aux = l.ini;
	
	while ((aux->sig != NULL) && (aux->elem < x))
	{
		aux = aux->sig;
	}
	
	b = (aux->elem == x);
	
	
	return b;
		
}

bool esNulaLista(tipoLista l)
{
	return l.ini == NULL;
}

void concatenar (tipoLista *l1, tipoLista *l2)
{
	int x;
	
	while (!(esNulaLista(*l2)))
	{
		x = consultarMenor(*l2);
		insertar(l1,x);
		eliminarMenor(l2);
	}
	
}

void imprimirLista (tipoLista l)
{
	celdaLista* aux;
	
	aux = l.ini;
	
	while (aux != NULL)
	{
		printf(" %d",aux->elem);
		aux = aux->sig;
	}
	printf("\n");
}
