#ifndef SYMBOL_TABLE_H
#define SYMBOL_TABLE_H

    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    #include <stdbool.h>

    typedef struct Simbolo {
        char *nombre;
        int tipo;
        int sid;
        bool entrada;
        bool salida;
        struct Simbolo *siguiente;
    } Simbolo;

    typedef struct {
        Simbolo *primero;
        Simbolo *ultimo;
        int ultimo_id;
    } TablaSimbolos;

    TablaSimbolos *inicializarTablaSimbolos();

    void insertarSimbolo(TablaSimbolos *tabla, char *nombre, int tipo);

    Simbolo *buscarSimbolo(TablaSimbolos *tabla, char *nombre);

    Simbolo *buscarSimboloPorSID(TablaSimbolos *tabla, int sid);

    void imprimirTablaSimbolos(TablaSimbolos *tabla);

    void liberarTablaSimbolos(TablaSimbolos *tabla);

    char *nombreTipo(int tipo);

#endif
