#ifndef LISTA_VALORES_H
#define LISTA_VALORES_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "definiciones.h"

typedef struct NodoValor {
    int sid;
    int tipo;
    int valor_entero;
    float valor_real;
    char valor_car;
    char *valor_str;
    struct NodoValor *siguiente;
} NodoValor;

typedef struct {
    NodoValor *primero;
    NodoValor *ultimo;
} ListaValores;

ListaValores *crearListaValores();
void insertarValor(ListaValores *lista, int sid, int tipo, void *valor);
char *recogerValor(ListaValores *lista, int sid);
NodoValor *buscarValor(ListaValores *lista, int sid);
void imprimirListaValores(ListaValores *lista);
void liberarListaValores(ListaValores *lista);

#endif // LISTA_VALORES_H
