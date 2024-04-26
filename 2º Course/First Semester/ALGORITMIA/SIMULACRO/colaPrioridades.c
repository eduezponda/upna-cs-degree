#include "colaPrioridades.h"
#include <stdio.h>
#include <stdlib.h>


void nuevaCola(tipoCola *c)
{
	c->ini = NULL;
	c->fin = NULL;
	c->ultimo_prioritario = NULL;
	c->n_prioritarios = 0;
	c->n_secundarios = 0;
}

bool hayPrioritarios(tipoCola c)
{
	return (c.n_prioritarios == 0);
}

bool haySecundarios(tipoCola c)
{
	return (c.n_secundarios == 0);
}

bool esNulaCola(tipoCola c)
{
	return c.ini == NULL;
}

void encolar(tipoCola *c, tipoValorElemento elem, tipoPrioridadElemento prior)
{
	celdaCola *aux;
	aux = malloc(sizeof(celdaCola));
	aux->elem = elem;
	aux->prioridad = prior;
	
	if(esNulaCola(*c))
	{
		if (prior)
		{
			aux->sig = NULL;
			c->ini = aux;
			c->fin = aux;
			c->ultimo_prioritario = aux;
			c->n_prioritarios = 1;
		}
		else
		{
			aux->sig = NULL;
			c->ini = aux;
			c->fin = aux;
			c->n_secundarios = 1;
		}
	}
	else
	{
		if (prior)
		{
			if (c->n_prioritarios == 0)
			{
				aux->sig = c->ini;
				c->ini = aux;
				c->ultimo_prioritario = aux;
			}
			else
			{
				aux->sig = c->ultimo_prioritario->sig;
				c->ultimo_prioritario->sig = aux;
				c->ultimo_prioritario = aux;
				if (c->n_secundarios == 0)
				{
					c->fin = aux;
				}
			}
			c->n_prioritarios = c->n_prioritarios + 1;
		}
		else
		{
			
			aux->sig = NULL;
			c->fin->sig = aux;
			c->fin = aux;
			c->n_secundarios = c->n_secundarios + 1;
			
			
		}
	}
		
	
		
	
	
}

void desencolar(tipoCola *c)
{
	if (!esNulaCola(*c))
	{
		celdaCola *aux;
		aux = c->ini;
		if (aux->prioridad)
		{
			c->n_prioritarios = c->n_prioritarios - 1;
		}
		else
		{
			c->n_secundarios = c->n_secundarios - 1;
		}
		c->ini = c->ini->sig;
		
		if (c->ini == NULL)
		{
			c->fin = NULL;
		}
		free(aux);
	}
	else
	{
		printf("No se puede desencolar de una cola nula\n");
	}
}

tipoValorElemento frente(tipoCola c)
{
	return c.ini->elem;
}

int nPrioritarios(tipoCola c)
{
	return c.n_prioritarios;
}

int nSecundarios(tipoCola c)
{
	return c.n_secundarios;
}
