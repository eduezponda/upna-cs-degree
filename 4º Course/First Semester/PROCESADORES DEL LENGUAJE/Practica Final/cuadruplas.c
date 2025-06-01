#include "cuadruplas.h"

TablaCuadruplas *crearTablaCuadruplas() {
    TablaCuadruplas *tabla = (TablaCuadruplas *)malloc(sizeof(TablaCuadruplas));
    if (!tabla) {
        fprintf(stderr, "Error: No se pudo asignar memoria para la tabla de cuádruplas.\n");
        exit(EXIT_FAILURE);
    }
    tabla->primero = NULL;
    tabla->ultimo = NULL;
    tabla->contadorTemporal = 0;
    return tabla;
}

void destruirTablaCuadruplas(TablaCuadruplas *tabla) {
    Cuadrupla *actual = tabla->primero;
    while (actual) {
        Cuadrupla *temp = actual;
        actual = actual->siguiente;

        free(temp);
    }
    free(tabla);
}

int siguienteQuad(TablaCuadruplas *tabla) {
    if (tabla->ultimo) {
        return tabla->ultimo->quad + 1;
    }
    return 0;
}

Cuadrupla *crearCuadrupla(Operacion operacion, int operando1, int operando2, int resultado, int quad) {
    Cuadrupla *cuadrupla = (Cuadrupla *)malloc(sizeof(Cuadrupla));
    if (!cuadrupla) {
        fprintf(stderr, "Error: No se pudo asignar memoria para la cuádrupla.\n");
        exit(EXIT_FAILURE);
    }
    cuadrupla->quad = quad;
    cuadrupla->operacion = operacion;
    cuadrupla->operando1 = operando1;
    cuadrupla->operando2 = operando2;
    cuadrupla->resultado = resultado;
    cuadrupla->siguiente = NULL;
    return cuadrupla;
}

void insertarCuadrupla(TablaCuadruplas *tabla, Cuadrupla *nuevoNodo) {
    if (!tabla->primero) {
        tabla->primero = nuevoNodo;
    } else {
        tabla->ultimo->siguiente = nuevoNodo;
    }
    tabla->ultimo = nuevoNodo;
}

char *generarTemporal(TablaCuadruplas *tabla) {
    char *temporal = (char *)malloc(16 * sizeof(char)); // Suficiente espacio para "t" + número
    if (!temporal) {
        fprintf(stderr, "Error: No se pudo asignar memoria para el temporal.\n");
        exit(EXIT_FAILURE);
    }
    sprintf(temporal, "t%d", tabla->contadorTemporal++);
    return temporal;
}

Cuadrupla *buscarCuadruplaPorQuad(TablaCuadruplas *tabla, int quad) {
    Cuadrupla *actual = tabla->primero;
    while (actual != NULL) {
        if (quad == actual->quad) {
            return actual;
        }
        actual = actual->siguiente;
    }
    return NULL;
}

void imprimirTablaCuadruplas(TablaCuadruplas *tabla) {
    Cuadrupla *actual = tabla->primero;
    while (actual) {
        printf("%d: (%d := %d, %d [%d])\n",
                actual->quad,
                actual->resultado,
                actual->operando1,
                actual->operando2,
                actual->operacion);
        actual = actual->siguiente;
    }
}

char *signoOperacion(int operacion) {
    switch (operacion) {
        case OP_SUMA:
            return "+";
        case OP_RESTA:
            return "-";
        case OP_RESTO:
            return "mod";
        case OP_MULTIPLICACION:
            return "*";
        case OP_DIVISION_ENTERA:
            return "div";
        case OP_DIVISION_REAL:
            return "/";
        case OP_ASIGNACION:
            return ":=";
        case OP_COMPARACION:
            return "comparacion";
        case OP_INT_TO_FLOAT:
            return "(entero) -> (real)";
        case OP_CAMBIO_SIGNO:
            return "-";
        case OP_SALTO:
            return "jmp";
        case OP_SALTO_CONDICIONAL:
            return "if";
        default:
            return "(operacion no encontrada)";
        }
    }

    char *signoComparacion(int comparacion) {
        switch (comparacion) {
            case TIPO_COMPARADOR_MENOR:
                return "<";
            case TIPO_COMPARADOR_MAYOR:
                return ">";
            case TIPO_COMPARADOR_MENOR_IGUAL:
                return "<=";
            case TIPO_COMPARADOR_MAYOR_IGUAL:
                return ">=";
            case TIPO_COMPARADOR_DISTINTO:
                return "<>";
            case TIPO_COMPARADOR_IGUAL:
                return "=";
            default:
                return "(comparación no encontrada)";
        }
    }
