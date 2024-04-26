#include<stdio.h>
#include<stdbool.h>
#include<stdlib.h>

typedef int tipoElementoLista;
typedef struct celdaL{
	tipoElementoLista elem;
    int prioridad;
	struct celdaL *sig;
}celdaLista;
typedef struct tipoL{
	celdaLista *ini;
	celdaLista *fin;
}tipoLista;

void insertar (tipoLista *l, int e, int prior);

void desencolar (tipoLista *l);

int cima (tipoLista l);

bool esListaVacia (tipoLista l);

void vaciarLista (tipoLista *l);

void nuevaLista (tipoLista *l);

void mostrarLista (tipoLista l);

int main(){
	int e, prior;
	tipoLista lista;
	int opcion;
    bool b;

	nuevaLista(&lista);
	do
    {
		printf("\n--------MENU-------- \n");
		printf("1 - Encolar un entero\n");
		printf("2 - Desencolar\n");
		printf("3 - Mostrar cima\n");
		printf("4 - ¿Cola vacía?\n");
		printf("5 - Vaciar la cola\n");
		printf("6 - Mostrar lista\n");
        printf("7 - Salir programa\n");
		printf("Escoja una opcion: ");
		scanf("%d",&opcion);
		switch(opcion){
			case 1:
				printf("Introduce el entero: ");
				scanf("%d",&e);
                printf("Introduce la prioridad: ");
                scanf("%d",&prior);
				insertar(&lista,e,prior);
				break;
			case 2:
				desencolar(&lista);
				break;
			case 3:
				e = cima(lista);
				printf("%d",e);
				break;
			case 4:
			
				b = esListaVacia (lista);
				
				if (b)
				{
					printf("La lista está vacía\n");
				}
				else
				{
					printf("La lista NO esta vacía\n");
				}
				
				break;
			case 5:
				vaciarLista(&lista);
				printf("Lista liberada\n");
				break;
			case 6:
				mostrarLista(lista);
				break;
			

		}
	}while(opcion<7 && opcion >0);
}
void insertar (tipoLista *l, int e, int prior)
{
	celdaLista *aux, *aux2, *auxant;
	
	aux = malloc(sizeof(celdaLista));
	aux->elem = e;
	aux->prioridad = prior;
	
	if (esListaVacia(*l))
	{
		aux->sig = NULL;
		l->ini = aux;
		l->fin = aux;
	}
	else
	{
		aux2 = l->ini;
		
		while (aux2 != NULL && aux2->prioridad > prior)
		{
			auxant = aux2;
			aux2 = aux2->sig;
		}
		if (aux2 == NULL)
		{
			aux->sig = NULL;
			l->fin->sig = aux;
			l->fin = aux;
		}
		else if (aux2->prioridad == prior)
		{
			while (aux2 != NULL && aux2->prioridad == prior)
			{
				auxant = aux2;
				aux2 = aux2->sig;
			}
			if (aux2 == NULL)
			{
				aux->sig = NULL;
				l->fin->sig = aux;
				l->fin = aux;
			}
			else
			{
				aux->sig = aux2;
				auxant->sig = aux;
			}
		}
		else
		{
			if (prior > l->ini->prioridad)
			{
				aux->sig = l->ini;
				l->ini = aux;
			}
			else
			{
				auxant->sig = aux;
				aux->sig = aux2;
			}
		}
	}
}
void desencolar (tipoLista *l)
{
	celdaLista* aux;
	
	if(esListaVacia(*l))
    {
        printf("ERROR: No se puede desencolar de lista NULA\n");
    }
    
    else
    {
    	aux = l->ini;
		l->ini = l->ini->sig;
	
		if (esListaVacia(*l))
		{
			l->fin = NULL;
		}
		else
		{
	
		}
	
		free (aux);
    }
}
int cima (tipoLista l)
{
	if (esListaVacia(l))
	{
		printf("NO posible hacer cima lista nula\n");
		exit(1);
	}
	else
	{
		return (l.ini->elem);
	}
}
bool esListaVacia (tipoLista l)
{
	return (l.ini == NULL || l.fin == NULL);
}
void vaciarLista (tipoLista *l)
{
	celdaLista *aux;
	
	while (!esListaVacia(*l))
	{
		aux = l->ini;
		l->ini = l->ini->sig;
		free(aux);
	}
	l->fin = NULL;
}
void nuevaLista (tipoLista *l)
{
	l->ini = NULL;
	l->fin = NULL;
}
void mostrarLista (tipoLista l)
{
	celdaLista *aux;
	
	aux = l.ini;
	
	while (aux != NULL)
	{
		printf(" %d",aux->elem);
		aux = aux->sig;
	}
}
