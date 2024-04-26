#include <stdlib.h>
#include <string.h>
#include <stdio.h>


char **fragmenta(const char *cadena){
    char *copia, *palabra, **arg;
    int num_palabras, i;
    
    num_palabras = 1;    // recuerde que se necesita una posición adicional para almacenar el NULL del delimitador final
    i = 0;     
    
    copia = (char *) malloc( (strlen(cadena)+1) * sizeof(char));
    strcpy(copia, cadena);
    
    palabra = strtok(copia, " ");
    while (palabra != NULL) {
        if (strlen(palabra) > 0) num_palabras++;
        palabra = strtok(NULL, " ");
    }
    
    arg = (char **) malloc( num_palabras * sizeof(char *) );
    
    palabra = strtok((char *) cadena, " ");
    while (palabra != NULL) {
        if (strlen(palabra) > 0) {
            arg[i] = (char *) malloc((strlen(palabra)+1) * sizeof(char));
            strcpy(arg[i], palabra);
            i++;
        }
        palabra = strtok(NULL, " ");
    }

    return arg;	
}

void borrarg(char **arg){
    int i;
    
    i = 0;
    while(arg[i] != NULL){
        free(arg[i]);
        i++;
    }
    free(arg);
}
void salida() //mostrar mensaje salida y sales de la minishell
{
	printf("Has salido de la minishell\n");
	exit(-1);
}
