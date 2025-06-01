#ifndef CUADRUPLAS_H
#define CUADRUPLAS_H

    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    #include "definiciones.h"

    typedef enum {
        OP_SUMA,
        OP_RESTA,
        OP_RESTO,
        OP_MULTIPLICACION,
        OP_DIVISION_ENTERA,
        OP_DIVISION_REAL,
        OP_ASIGNACION,
        OP_COMPARACION,
        OP_INT_TO_FLOAT,
        OP_CAMBIO_SIGNO,
        OP_SALTO,
        OP_SALTO_CONDICIONAL,
    } Operacion;

    typedef struct Cuadrupla {
        Operacion operacion;
        int operando1;
        int operando2;
        int resultado;
        int quad;
        struct Cuadrupla *siguiente;
    } Cuadrupla;

    typedef struct TablaCuadruplas {
        Cuadrupla *primero;
        Cuadrupla *ultimo;
        int contadorTemporal;
    } TablaCuadruplas;

    TablaCuadruplas *crearTablaCuadruplas();
    int siguienteQuad(TablaCuadruplas *tabla);
    void destruirTablaCuadruplas(TablaCuadruplas *tabla);
    Cuadrupla *crearCuadrupla(Operacion operacion, int operando1, int operando2, int resultado, int quad);
    void insertarCuadrupla(TablaCuadruplas *tabla, Cuadrupla *cuadrupla);
    char *generarTemporal(TablaCuadruplas *tabla);
    Cuadrupla *buscarCuadruplaPorQuad(TablaCuadruplas *tabla, int quad);
    void imprimirTablaCuadruplas(TablaCuadruplas *tabla);
    char *signoOperacion(int operacion);
    char *signoComparacion(int comparacion);

#endif // CUADRUPLAS_H
