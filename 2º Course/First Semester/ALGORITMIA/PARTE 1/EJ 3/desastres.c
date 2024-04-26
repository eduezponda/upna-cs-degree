#include <stdio.h>
#include "pilaRegistrosDinamica.h"
#define N 100


typedef Registro tabla[N];

void mostrarPantalla (Registro r1);
void buscarFechaUlt(tipoPila* pila, Registro* r1);

int main()
{
    tabla r;
    tipoPila pila;
    int i, numDesastres;
    char res;
    do
    {

        printf("Introduce el número de desastres:\n");
        scanf("%d",&numDesastres);

        printf("Introduce la fecha y el número de víctimas: ");
        scanf("%d",&r[0].fecha);
        scanf("%d",&r[0].numVictimas);
        r[0].fechaUlt = 0;
        
        mostrarPantalla(r[0]);
        nuevaPila(&pila);
        apilar(&pila,r[0]);

        for (i = 1; i < numDesastres; i = i + 1)
        {
            printf("Introduce la fecha y el número de víctimas: ");
            scanf("%d",&r[i].fecha);
            scanf("%d",&r[i].numVictimas);
            
            if(r[i].numVictimas < (cima(pila)).numVictimas)
            {
            	r[i].fechaUlt = (cima(pila)).fecha;
            	mostrarPantalla(r[i]);
            	apilar(&pila,r[i]);
            }
			else
			{
            	buscarFechaUlt(&pila,&r[i]);
            	apilar(&pila,r[i]);
            }

      
        }

    printf("¿Deseas continuar? s/n\n");
    scanf(" %c",&res);

    }while (res == 's' || res == 'S');

    return(0);
}

void mostrarPantalla (Registro r1)
{
	printf("%d %d %d\n",r1.fecha,r1.numVictimas,r1.fechaUlt);
}
void buscarFechaUlt(tipoPila* pila, Registro* r1)
{
	Registro x;
	x=cima(*pila);
	desapilar(pila);
	
	while ((!(esNulaPila(*pila))) && (r1->numVictimas>=x.numVictimas))
	{
		x=cima(*pila);
		if (r1->numVictimas>=x.numVictimas)
		{
			desapilar(pila);
		}
		else
		{
			
		}
	}
	if (x.numVictimas>r1->numVictimas)
	{
		r1->fechaUlt = x.fecha;
		mostrarPantalla(*r1);
	}
	 
	else
	{
		r1->fechaUlt = 0;
		mostrarPantalla(*r1);
	}	
}
