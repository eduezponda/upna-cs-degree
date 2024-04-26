#include <stdio.h>
#include "colaDobleCaracteres.h"
#include <stdlib.h>

void nuevaColaDoble(tipoColaDoble *c)
{
	c->ini = NULL;
	c->fin = NULL;
}

void encolarPrimero(tipoColaDoble *c, tipoElementoColaDoble x)
{
	celdaColaDoble* aux;
	
	aux = (celdaColaDoble*)malloc(sizeof(celdaColaDoble));
	aux->elem = x;
	aux->sig = c->ini;
	aux->ant = NULL;
	
	if (c->ini == NULL)
	{
		c->fin = aux; 
	}
	else
	{
		c->ini->ant = aux;
	}
	c->ini = aux;
}	

void encolarUltimo(tipoColaDoble *c, tipoElementoColaDoble x)
{
	celdaColaDoble* aux;
	
	aux = (celdaColaDoble*)malloc(sizeof(celdaColaDoble));
	aux->elem = x;
	aux->sig = NULL;
	aux->ant = c->fin ;
	
	if (c->ini == NULL)
	{
		c->ini = aux;
	}
	else
	{
		c->fin->sig = aux;
	}
	c->fin = aux;
}

void desencolarPrimero(tipoColaDoble *c)
{	
	celdaColaDoble* aux;

	if (c->ini == NULL)
	{
		printf("No se puede desencolar en una cola nula\n");
		exit(1);
	}
	else
	{
		aux = c->ini;
		c->ini = c->ini->sig;

		if (c->ini == NULL)
		{
			c->fin = NULL;
		}
		else
		{
			c->ini->ant = NULL;
		}
		
		free(aux);
		
	}
}

void desencolarUltimo(tipoColaDoble *c)
{
	
	celdaColaDoble* aux;
	
	if (c->ini == NULL)
	{
		printf("No se puede desencolar en una cola nula\n");
		exit(1);
	}
	else
	{
		aux = c->fin;
		c->fin = c->fin->ant;

		if (c->fin == NULL)
		{
			c->ini = NULL;
		}
		else
		{
			c->fin->sig = NULL;
		}
		
		free(aux);
		
	}
}

tipoElementoColaDoble elemPrimero(tipoColaDoble c)
{
	if (c.ini == NULL)
	{
		printf("No se puede mostrar ningun elemento de una cola nula\n");
		exit(1);
	}
	else
	{
		return (c.ini->elem);
	}
}

tipoElementoColaDoble elemUltimo(tipoColaDoble c)
{
	if (c.ini == NULL)
	{
		printf("No se puede mostrar ningun elemento de una cola nula\n");
		exit(1);
	}
	else
	{
		return (c.fin->elem);
	}
}

bool esNulaColaDoble(tipoColaDoble c)
{
	return (c.ini == NULL);
}
