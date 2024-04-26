#include <stdio.h>
#include <string.h>
#include "pilaCaracteresDinamica.h" 
#include "colaDobleCaracteres.h"
#include <stdlib.h>
#include <stdbool.h>

typedef char f[100];

void modificarFrase1(tipoPila* p,tipoColaDoble* c);
bool esVocal(char x);
void modificarFrase2 (tipoColaDoble* c, f frase2);
void decodificarFrase2 (tipoColaDoble* c2, tipoColaDoble* c3, tipoPila *p2);
void introducirCola(tipoColaDoble* c2,f frase2);
void liberarCola(tipoColaDoble* c);

int main ()
{
	int i;
	tipoColaDoble c, c2, c3, c4;
	tipoPila p;
	celdaColaDoble* aux;
	f frase, frase2;
	char res;
    
    
    nuevaColaDoble(&c);
    nuevaColaDoble(&c2);
    nuevaColaDoble(&c3);
    nuevaColaDoble(&c4);
    nuevaPila(&p);
    

    do
    {
    	printf("Introduce la frase que quieres codificar: ");
    	scanf("%[^\n]",frase);
        i = 0;
            
        while (i < strlen(frase))
        {
            if (esVocal(frase[i]))
            {
            	if (esNulaPila(p))
            	{
            		encolarUltimo(&c,frase[i]);
            	}
            	else
            	{
            		modificarFrase1(&p,&c);
            		encolarUltimo(&c,frase[i]);
            	}
            }
            else
            {
            	apilar(&p,frase[i]);
            }
            	
            i = i + 1;	
        }
            
        modificarFrase1(&p,&c);
            	
		printf("Tras la primera codificación el mensaje es: ");
		aux = c.ini;
		while (aux != NULL)
		{
			printf("%c",aux->elem);
			aux = aux->sig;
		}
		
		printf("\n");
		
		printf("Tras la segunda codificación el mensaje es: ");
		
		modificarFrase2(&c,frase2);
		
		printf("\n");
		
		printf("Tras la primera decodificación el mensaje es: ");
		
		introducirCola(&c2,frase2);
		decodificarFrase2(&c2,&c3,&p);
		
		printf("\n");
		
		printf("Tras la segunda decodificación el mensaje es: ");
		
		aux = c3.ini;
		
		while (aux != NULL)
        {
            if (esVocal(aux->elem))
            {
            	if (esNulaPila(p))
            	{
            		encolarUltimo(&c4,aux->elem);
            	}
            	else
            	{
            		modificarFrase1(&p,&c4);
            		encolarUltimo(&c4,aux->elem);
            	}
            }
            else
            {
            	apilar(&p,aux->elem);
            }
            	
            aux = aux->sig;
        }
            
        modificarFrase1(&p,&c4);
        
        aux = c4.ini;
		while (aux != NULL)
		{
			printf("%c",aux->elem);
			aux = aux->sig;
		}
		
		liberarCola(&c);
		liberarCola(&c2);
		liberarCola(&c3);
		liberarCola(&c4);
		
        printf("\n");
		
        printf("¿Deseas continuar? s/n\n");
        scanf(" %c",&res);

    } while(res == 's' || res == 'S');

    return 0;
}


void modificarFrase1(tipoPila* p,tipoColaDoble* c)
{
	char x;
	
	while (!(esNulaPila(*p)))
	{
		x = cima(*p);
		desapilar(p);
		encolarUltimo(c,x);
	}
}
bool esVocal(char x)
{
	return ((x == 'a') || (x == 'e') || (x == 'i') || (x == 'o') || (x == 'u') || (x == 'A') || (x == 'E') || (x == 'I') || (x == 'O') || (x == 'U'));
}
void modificarFrase2 (tipoColaDoble* c, f frase2)
{
	celdaColaDoble *aux1, *aux2;
	
	aux1 = c->ini;
	aux2 = c->fin;
	int i = 0;

	while(aux1 != aux2)
	{
		printf("%c",aux1->elem);
		frase2[i] = aux1->elem;
		i = i + 1;
		aux1 = aux1->sig;
		
		
		if (aux1 != aux2)
		{
			printf("%c",aux2->elem);
			frase2[i] = aux2->elem;
			i = i + 1;
			aux2 = aux2->ant;
		}
			
	}
	
	printf("%c",aux1->elem);
	frase2[i] = aux1->elem;
}
void decodificarFrase2 (tipoColaDoble* c2, tipoColaDoble* c3, tipoPila* p2)
{
	celdaColaDoble *aux1;
	char x;
	
	aux1 = c2->ini;
	
	while (aux1 != NULL)
	{
		encolarUltimo(c3,aux1->elem);
		printf("%c",aux1->elem);
		aux1 = aux1->sig;
		
		if (aux1 != NULL)
		{
			apilar(p2,aux1->elem);
			aux1 = aux1->sig;
		}
	}
	
	while(!(esNulaPila(*p2)))
	{
		x = cima(*p2);
		encolarUltimo(c3,x);
		printf("%c",x);
		desapilar(p2);
		
	}
	
}
void introducirCola(tipoColaDoble* c2,f frase2)
{
	int i;
	i = 0;
	
	while (i < strlen(frase2))
	{
		encolarUltimo(c2,frase2[i]);
		i = i + 1;
	}
}

void liberarCola(tipoColaDoble* c)
{
	
	while (!(esNulaColaDoble(*c)))
	{
		desencolarPrimero(c);
	}
}



