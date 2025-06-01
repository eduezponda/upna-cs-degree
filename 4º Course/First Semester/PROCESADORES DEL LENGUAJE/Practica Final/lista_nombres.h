#ifndef INCLUSION_DEFINICIONES
#define INCLUSION_DEFINICIONES

    #include <stdlib.h>
    #include <stdio.h>
    #include <string.h>
    #include "definiciones.h"
    #include "lista_etiquetas.h"

    // Estructura nombres
    typedef struct NodoNombre {
        char *nombre;
        struct NodoNombre *siguiente;
    } NodoNombre;

    typedef struct {
        NodoNombre *primero;
        NodoNombre *ultimo;
    } ListaNombres;

    // Otras estructuras
    typedef struct {
        int tipo;
        int sid;
    } Tipo_y_variable;

    typedef struct {
        ListaEtiquetas *t;
        ListaEtiquetas *f;
    } Expresion;

    typedef struct {
        ListaEtiquetas *next;
        int quad;
    } NextConQuad;

    // Funciones
    ListaNombres *inicializarListaNombres();
    void agregarNombre(ListaNombres *lista, char *nombre);
    void liberarListaNombres(ListaNombres *lista);
    ListaNombres *unirListaNombres(ListaNombres *lista1, ListaNombres *lista2);
    
#endif
