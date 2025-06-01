#include "lista_nombres.h"

ListaNombres *inicializarListaNombres() {
    ListaNombres *lista = (ListaNombres *)malloc(sizeof(ListaNombres));
    lista->primero = lista->ultimo = NULL;
    return lista;
}

void agregarNombre(ListaNombres *lista, char *nombre) {
    NodoNombre *nuevo = (NodoNombre *)malloc(sizeof(NodoNombre));
    nuevo->nombre = strdup(nombre);
    nuevo->siguiente = NULL;
    if (lista->ultimo) {
        lista->ultimo->siguiente = nuevo;
        lista->ultimo = nuevo;
    } else {
        lista->primero = lista->ultimo = nuevo;
    }
}

void liberarListaNombres(ListaNombres *lista) {
    NodoNombre *actual = lista->primero;
    while (actual) {
        NodoNombre *tmp = actual;
        actual = actual->siguiente;
        free(tmp->nombre);
        free(tmp);
    }
    lista->primero = lista->ultimo = NULL;
}

ListaNombres *unirListaNombres(ListaNombres *lista1, ListaNombres *lista2) {
    if (!lista2 || !lista2->primero) {
        return lista1;
    }
    ListaNombres *lista = inicializarListaNombres();

    NodoNombre *actual = lista1->primero;
    while (actual) {
        agregarNombre(lista, actual->nombre);
        actual = actual->siguiente;
    }
    actual = lista2->primero;
    while (actual) {
        agregarNombre(lista, actual->nombre);
        actual = actual->siguiente;
    }

    return lista;
}