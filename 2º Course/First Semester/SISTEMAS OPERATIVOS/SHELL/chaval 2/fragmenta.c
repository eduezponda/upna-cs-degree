
#include <stdlib.h>
#include <string.h>
#include "fragmenta.h"

char **fragmenta(const char *cadena) //Funcion que fragmenta el string en palabras separadas 
{
	char **palabras;
	char *palabra, *aux;
	aux = (char *)malloc(strlen(cadena) + 1); 
	strcpy(aux, cadena);
	palabra = strtok((char *)cadena, " ");
	int contador = 1; 
	while (palabra != NULL)
	{
		if (strlen(palabra) > 0)
		{
			contador++;
		}
		palabra = strtok(NULL, " ");
	}
	palabras = (char **)malloc(contador * sizeof(char *));
	palabra = strtok(aux, " ");
	int pos;
	pos = 0;
	while (palabra != NULL)
	{
		if (strlen(palabra) > 0)
		{
			palabras[pos] = (char *)malloc((strlen(palabra) + 1) * 1); 
			strcpy(palabras[pos], palabra);
			pos++;
		}
		palabra = strtok(NULL, " ");
	}
	palabras[pos] = NULL;
	return palabras;
}


void salida() { //Funcion salida para mostrar el mensaje y finalizar el programa
    printf("Ha salido de la minishell\n");
    exit(-1);
}

void borrarg(char** arg) //Funcion borrarg para limpiar los espacios de memoria usados
{
    for(int q = 0; arg[q] != NULL; q = q + 1)
    {
        free(arg[q]);
    }
    free(arg);
}
