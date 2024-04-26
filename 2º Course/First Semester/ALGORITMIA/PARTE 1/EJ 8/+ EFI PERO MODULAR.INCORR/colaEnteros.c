#include "colaEnteros.h"
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

void nuevaCola(tipoCola* c)
{	
	c->ini = NULL;
	c->fin = NULL;
}

bool esNulaCola(tipoCola c) 
{
	return (c.ini == NULL);
}

void encolar(tipoCola* c, tipoElementoCola e) 
{
	celdaCola* aux;
	
	aux = (celdaCola*)malloc(sizeof(celdaCola));
	aux->elem = e;
	aux->sig = NULL;
	
	if (esNulaCola(*c))
	{
		c->ini = aux;
	}
	else
	{
		c->fin->sig = aux;
	}
	c->fin = aux;
}

void desencolar(tipoCola* c)
{
	celdaCola* aux;
	
	if(esNulaCola(*c))
    {
        printf("\nERROR: No se puede desencolar de cola NULA\n");
    }
    
    else
    {
    	aux = c->ini;
		c->ini = c->ini->sig;
	
		if (esNulaCola (*c))
		{
			c->fin = NULL;
		}
		else
		{
	
		}
	
		free (aux);
    }
	
	
}

tipoElementoCola frente(tipoCola c)
{
	if (esNulaCola(c))
	{
		printf("\nERROR: No existe frente de cola NULA\n");
	}
	
	return (c.ini->elem);
	
}

