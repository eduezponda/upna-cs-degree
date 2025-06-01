#include "tabla_simbolos.h"


TablaSimbolos *inicializarTablaSimbolos() {
    TablaSimbolos *tabla = (TablaSimbolos *)malloc(sizeof(TablaSimbolos));
    tabla->primero = NULL;
    tabla->ultimo = NULL;
    return tabla;
}

void insertarSimbolo(TablaSimbolos *tabla, char *nombre, int tipo) {

    if (!tabla->ultimo_id) {
        tabla->ultimo_id = 0;
    }

    Simbolo *nuevo = (Simbolo *)malloc(sizeof(Simbolo));
    if (nombre) nuevo->nombre = strdup(nombre);
    else nuevo->nombre = NULL;
    nuevo->tipo = tipo;
    nuevo->sid = ++(tabla->ultimo_id);
    nuevo->siguiente = NULL;

    if (tabla->primero == NULL) {
        tabla->primero = nuevo;
        tabla->ultimo = nuevo;
    } else {
        tabla->ultimo->siguiente = nuevo;
        tabla->ultimo = nuevo;
    }
}

Simbolo *buscarSimbolo(TablaSimbolos *tabla, char *nombre) {
    Simbolo *actual = tabla->primero;
    while (actual) {
        if (actual->nombre && strcmp(actual->nombre, nombre) == 0) {
            return actual;
        }
        actual = actual->siguiente;
    }
    return NULL;
}

Simbolo *buscarSimboloPorSID(TablaSimbolos *tabla, int sid) {
    Simbolo *actual = tabla->primero;
    while (actual != NULL) {
        if (sid == actual->sid) {
            return actual;
        }
        actual = actual->siguiente;
    }
    return NULL;
}

void imprimirTablaSimbolos(TablaSimbolos *tabla) {
    Simbolo *actual = tabla->primero;
    printf("Tabla de Símbolos:\n");
    while (actual != NULL) {
        char *nombre;
        if (actual->nombre) nombre = actual->nombre;
        else nombre = "";
        printf("Nombre: %s, Tipo: %d, Sid: %d\n", nombre, actual->tipo, actual->sid);
        actual = actual->siguiente;
    }
}

void liberarTablaSimbolos(TablaSimbolos *tabla) {
    Simbolo *actual = tabla->primero;
    Simbolo *siguiente;
    while (actual != NULL) {
        siguiente = actual->siguiente;
        free(actual->nombre);
        free(actual);
        actual = siguiente;
    }
    tabla->primero = NULL;
    tabla->ultimo = NULL;
    tabla->ultimo_id = 0;
}

char *nombreTipo(int tipo) {
    switch (tipo) {
        case 0:
            return "entero";
        case 1:
            return "cadena";
        case 2:
            return "carácter";
        case 3:
            return "real";
        case 4:
            return "booleano";
        default:
            return "(tipo no encontrado)";
    }
}
