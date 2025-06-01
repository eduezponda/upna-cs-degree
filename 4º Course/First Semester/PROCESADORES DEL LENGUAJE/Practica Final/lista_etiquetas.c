#include "lista_etiquetas.h"

ListaEtiquetas *crearLista(int etiqueta) {
    ListaEtiquetas *nueva = (ListaEtiquetas *)malloc(sizeof(ListaEtiquetas));
    if (!nueva) {
        fprintf(stderr, "Error al asignar memoria para la lista de etiquetas.\n");
        exit(EXIT_FAILURE);
    }
    nueva->etiqueta = etiqueta;
    nueva->siguiente = NULL;
    return nueva;
}

ListaEtiquetas* unirListas(ListaEtiquetas *l1, ListaEtiquetas *l2) {
    if (!l2) { return l1; }
    if (!l1) { return l2; }

    ListaEtiquetas *actual = l1;
    while (actual->siguiente) {
        actual = actual->siguiente;
    }
    
    actual->siguiente = l2;
    return l1;
}


void backpatch(ListaEtiquetas *lista, TablaCuadruplas *tabla, int destino) {
    ListaEtiquetas *actual = lista;
    while (actual) {
        Cuadrupla *cuadrupla = buscarCuadruplaPorQuad(tabla, actual->etiqueta);
        cuadrupla->resultado = destino;
        actual = actual->siguiente;
    }
}

void imprimirListaEtiquetas(const ListaEtiquetas *lista) {
    const ListaEtiquetas *actual = lista;
    printf("Lista de etiquetas: ");
    while (actual) {
        printf("%d ", actual->etiqueta);
        actual = actual->siguiente;
    }
    printf("\n");
}

void liberarListaEtiquetas(ListaEtiquetas *lista) {
    while (lista) {
        ListaEtiquetas *temp = lista;
        lista = lista->siguiente;
        free(temp);
    }
}
