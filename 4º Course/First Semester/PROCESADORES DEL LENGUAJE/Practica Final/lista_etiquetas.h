#ifndef LISTA_ETIQUETAS_H
#define LISTA_ETIQUETAS_H

    #include <stdlib.h>
    #include <stdio.h>
    #include "cuadruplas.h"

    typedef struct ListaEtiquetas {
        int etiqueta;
        struct ListaEtiquetas *siguiente;
    } ListaEtiquetas;

    ListaEtiquetas *crearLista(int etiqueta);
    ListaEtiquetas *unirListas(ListaEtiquetas *l1, ListaEtiquetas *l2);
    void backpatch(ListaEtiquetas *lista, TablaCuadruplas *tabla, int destino);
    void imprimirListaEtiquetas(const ListaEtiquetas *lista);
    void liberarListaEtiquetas(ListaEtiquetas *lista);

#endif // LISTA_ETIQUETAS_H
