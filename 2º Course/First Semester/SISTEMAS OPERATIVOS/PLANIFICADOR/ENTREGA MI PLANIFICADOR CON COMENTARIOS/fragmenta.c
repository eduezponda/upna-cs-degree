#include "fragmenta.h"
char **fragmenta(char *cadena)
{
    int cont, i;
    char *token, *aux;
    char **lista;
    aux = (char *)malloc((strlen(cadena)+1) * sizeof(char *));
    strcpy(aux, cadena);
    cont = 0;
    token = strtok(cadena, " ");
    while (token != NULL)
    {
        if (strlen(token) > 0)
            cont ++;
        token = strtok(NULL, " ");
    }
    lista = (char **)malloc((cont+1) * sizeof(char *));
    token = strtok(aux, " ");
    i = 0;
    while (token != NULL)
    {
        if (strlen(token) > 0)
        {
            lista[i] = (char *)malloc(strlen(token)+1);
            strcpy(lista[i], token);
            i++;
        }
        token = strtok(NULL, " ");
    }
    lista[i] = NULL;
    return lista;
}
void borrar(char ** cadena)
{
    int i;
    i=0;
    while (cadena[i] != NULL)
    {
        free(cadena[i]);
        i++;
    }
    free(cadena);
}
