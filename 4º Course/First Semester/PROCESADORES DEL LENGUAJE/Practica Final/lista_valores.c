#include "lista_valores.h"

ListaValores *crearListaValores() {
    ListaValores *lista = (ListaValores *)malloc(sizeof(ListaValores));
    lista->primero = NULL;
    lista->ultimo = NULL;
    return lista;
}

void insertarValor(ListaValores *lista, int sid, int tipo, void *valor) {
    NodoValor *nuevo = (NodoValor *)malloc(sizeof(NodoValor));
    nuevo->sid = sid;
    nuevo->tipo = tipo;

    switch (tipo) {
        case TIPO_ENTERO:
            nuevo->valor_entero = *(int *)valor;
            break;
        case TIPO_REAL:
            nuevo->valor_real = *(float *)valor;
            break;
        case TIPO_CARACTER:
            nuevo->valor_car = *(char *)valor;
            break;
        case TIPO_CADENA:
            nuevo->valor_str = strdup((char *)valor);
            break;
        case TIPO_BOOL:
            nuevo->valor_str = strdup((char *)valor);
            break;
        default:
            free(nuevo);
            return;
    }

    if (!lista->primero) {
        lista->primero = nuevo;
    } else {
        lista->ultimo->siguiente = nuevo;
    }
    lista->ultimo = nuevo;
    nuevo->siguiente = NULL;
}

char *recogerValor(ListaValores *lista, int sid) {
    NodoValor *valor = buscarValor(lista, sid);
    if (!valor) {
        return strdup("(valor no encontrado)");
    }

    char buffer[128];
    char *resultado = NULL;

    switch (valor->tipo) {
        case TIPO_ENTERO:
            snprintf(buffer, sizeof(buffer), "%d", valor->valor_entero);
            resultado = strdup(buffer);
            break;
        case TIPO_REAL:
            snprintf(buffer, sizeof(buffer), "%.2f", valor->valor_real);
            resultado = strdup(buffer);
            break;
        case TIPO_CARACTER:
            snprintf(buffer, sizeof(buffer), "'%c'", valor->valor_car);
            resultado = strdup(buffer);
            break;
        case TIPO_CADENA:
            snprintf(buffer, sizeof(buffer), "\"%s\"", valor->valor_str);
            resultado = strdup(buffer);
            break;
        case TIPO_BOOL:
            resultado = strdup(valor->valor_str);
            break;
        default:
            resultado = strdup("(tipo desconocido)");
            break;
    }

    return resultado;
}


NodoValor *buscarValor(ListaValores *lista, int sid) {
    NodoValor *actual = lista->primero;
    while (actual) {
        if (actual->sid == sid) {
            return actual;
        }
        actual = actual->siguiente;
    }
    return NULL;
}

void imprimirListaValores(ListaValores *lista) {
    NodoValor *actual = lista->primero;
    while (actual) {
        printf("SID: %d, Tipo: %d, ", actual->sid, actual->tipo);
        switch (actual->tipo) {
            case TIPO_ENTERO:
                printf("Valor Entero: %d\n", actual->valor_entero);
                break;
            case TIPO_REAL:
                printf("Valor Real: %.2f\n", actual->valor_real);
                break;
            case TIPO_CARACTER:
                printf("Valor Caracter: %c\n", actual->valor_car);
                break;
            case TIPO_CADENA:
                printf("Valor Cadena: %s\n", actual->valor_str);
                break;
            case TIPO_BOOL:
                printf("Valor Booleano: %s\n", actual->valor_str);
                break;
        }
        actual = actual->siguiente;
    }
}

void liberarListaValores(ListaValores *lista) {
    NodoValor *actual = lista->primero;
    while (actual) {
        NodoValor *temp = actual;
        actual = actual->siguiente;
        if (temp->tipo == TIPO_CADENA && temp->valor_str) {
            free(temp->valor_str);
        }
        free(temp);
    }
    free(lista);
}
