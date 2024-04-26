#include <stdio.h>
#include "colaEnteros.h"
#include <stdlib.h>
#include <stdbool.h>
bool esUltimo (tipoCola c);

int main ()
{
    tipoCola c;
    int numRes, paso, i, x;
    
    char res;
    
    nuevaCola(&c);

    do
    {
    	printf("Introduce el número de residentes:");
    	scanf(" %d",&numRes);
    	
    	printf("Introduce el paso (cada cuantos se muere uno):");
    	scanf(" %d",&paso);
    	
    	i = 1;
    	
    	while (i <= numRes)
    	{
    		encolar(&c,i);
    		i = i + 1;
    	}
    	
    	i = 1;
    	
    	
    	while (!(esUltimo(c)))
    	{
    		if (i < paso)
    		{
    			x = frente(c);
    			desencolar(&c);
    			encolar(&c,x);
    			i = i + 1;
    		}
    		else
    		{
    			desencolar(&c);
    			i = 1;
    		}
    	}
    	
    	printf("El resistente que no muere es: %d\n", frente(c));
    	
    	desencolar(&c);

		printf("¿Deseas continuar? s/n\n");
		scanf(" %c",&res);

    }while (res == 's' || res == 'S');

    return 0;
}

bool esUltimo (tipoCola c)
{
	return c.ini->sig == NULL;
}



