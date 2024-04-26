#include "matrizDinamica.h"
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

void nuevaMatriz(tipoCola* c)
{	
	c->ini = NULL;
	c->fin = NULL;
}

bool esNulaMatriz(tipoCola c) 
{
	return (c.ini == NULL);
}

void encolar(tipoCola* c, celdaCola *aux) 
{
	aux->sig = NULL;
	
	if (esNulaMatriz(*c))
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
	
	if(esNulaMatriz(*c))
    {
        printf("\nERROR: No se puede desencolar de cola NULA\n");
    }
    
    else
    {
    	aux = c->ini;
		c->ini = c->ini->sig;
	
		if (esNulaMatriz (*c))
		{
			c->fin = NULL;
		}
		else
		{
	
		}
	
		free (aux);
    }
	
	
}

tipoElementoMatriz frente(tipoCola c)
{
	if (esNulaMatriz(c))
	{
		printf("\nERROR: No existe frente de cola NULA\n");
	}
	
	return (c.ini->elem);
	
}

