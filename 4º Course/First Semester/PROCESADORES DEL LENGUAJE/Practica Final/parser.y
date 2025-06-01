%{
    #include "tabla_simbolos.h"
    #include "lista_nombres.h"
    #include "cuadruplas.h"
    #include "lista_etiquetas.h"
    #include "definiciones.h"
    #include "lista_valores.h"

    TablaSimbolos *tabla_simbolos;
    TablaCuadruplas *tabla_cuadruplas;
    ListaValores *lista_valores;

    void yyerror(const char *s);
    extern int yylex();
    extern int yylineno;
%}

%union {
    char *nombre;
    int tipo;
    ListaNombres *lista;
    Expresion exp;
    ListaEtiquetas *next;
    struct {
        int tipo;
        int entero;
        float real;
        char caracter;
        char* cadena;
    } valor;
    struct {
        int tipo;
        int sid;
    } tipoConSID;
    NextConQuad next_con_quad;
}

%token <valor> VALOR VALOR_BOOL
%token <tipo> DIVISION COMPARADOR OPERADOR
%token <nombre> ID ID_BOOLEANO
%token <tipo> ENTERO REAL CADENA BOOLEANO CARACTER

%token ALGORITMO FALGORITMO NEGACION
%token SI SINO FSI MIENTRAS FMIENTRAS PARA FPARA
%token ASIGNACION COMA PARENTESIS_I PARENTESIS_D CONTINUAR
%token SUMA RESTA RESTO DEL_TIPO THEN CORCHETE_I CORCHETE_D
%token FIN_INSTRUCCION HASTA HACER MULTIPLICACION
%token VAR FVAR VALORES_ENTRADA VALORES_SALIDA VALORES_ENTRADA_SALIDA

%left SUMA RESTA NEGACION
%left DIVISION RESTO MULTIPLICACION

%type <lista> lista_variables lista_variables_bool declaracion lista_declaraciones
%type <tipo> tipo valores_booleanos id_o_valor_o_funcion_booleana m asignacion_entera
%type <tipoConSID> expresion expresion_sin_parentesis id_o_valor_o_funcion
%type <exp> expresion_booleana
%type <next> estructura_condicional estructura_iterativa n
%type <next_con_quad> estructura_sino condicion_para

%start algoritmo

%%

algoritmo:
    ALGORITMO ID FIN_INSTRUCCION cuerpo FALGORITMO {}
    ;

cuerpo:
    valores_entrada_salida declaraciones instrucciones {}
    ;

valores_entrada_salida:
    entrada salida entrada_salida {}
    ;

entrada:
    /* Lista vacía */
    | VALORES_ENTRADA lista_declaraciones
    {
        NodoNombre *actual = $2->primero;
        while (actual) {
            Simbolo *simbolo = buscarSimbolo(tabla_simbolos, actual->nombre);
            simbolo->entrada = true;
            simbolo->salida = false || simbolo->salida;
            actual = actual->siguiente;
        }
        liberarListaNombres($2);
    }
    ;

salida:
    /* Lista vacía */
    | VALORES_SALIDA lista_declaraciones
    {
        NodoNombre *actual = $2->primero;
        while (actual) {
            Simbolo *simbolo = buscarSimbolo(tabla_simbolos, actual->nombre);
            simbolo->entrada = false || simbolo->entrada;
            simbolo->salida = true;
            actual = actual->siguiente;
        }
        liberarListaNombres($2);
    }
    ;

entrada_salida:
    /* Lista vacía */
    | VALORES_ENTRADA_SALIDA lista_declaraciones
    {
        NodoNombre *actual = $2->primero;
        while (actual) {
            Simbolo *simbolo = buscarSimbolo(tabla_simbolos, actual->nombre);
            simbolo->entrada = true;
            simbolo->salida = true;
            actual = actual->siguiente;
        }
        liberarListaNombres($2);
    }
    ;

declaraciones:
    /* Lista vacía */
    | VAR lista_declaraciones FVAR
    {
        liberarListaNombres($2);
    }
    ;

lista_declaraciones:
    declaracion 
    {
        $$ = $1;
    }
    | lista_declaraciones declaracion 
    {
        $$ = unirListaNombres($1, $2);
    }
    ;

declaracion:
    lista_variables DEL_TIPO tipo FIN_INSTRUCCION
    {
        NodoNombre *actual = $1->primero;
        while (actual) {
            Simbolo *simbolo = buscarSimbolo(tabla_simbolos, actual->nombre);
            if (!simbolo) {
                insertarSimbolo(tabla_simbolos, actual->nombre, $3);
            }
            else {
                fprintf(stderr, "\nVariable '%s' ya declarada (linea %d).\n", actual->nombre, yylineno);
            }
            actual = actual->siguiente;
        }
        $$ = $1;
    }
    | lista_variables_bool DEL_TIPO BOOLEANO FIN_INSTRUCCION 
    {
        NodoNombre *actual = $1->primero;
        while (actual) {
            Simbolo *simbolo = buscarSimbolo(tabla_simbolos, actual->nombre);
            if (!simbolo) {
                insertarSimbolo(tabla_simbolos, actual->nombre, $3);
            }
            else {
                fprintf(stderr, "\nVariable '%s' ya declarada (linea %d).\n", actual->nombre, yylineno);
            }
            actual = actual->siguiente;
        }
        $$ = $1;
    }
    ;

lista_variables:
    ID
    {
        $$ = inicializarListaNombres();
        agregarNombre($$, $1);
    }
    | lista_variables COMA ID
    {
        agregarNombre($1, $3);
        $$ = $1;
    }
    ;

lista_variables_bool:
    ID_BOOLEANO
    {
        $$ = inicializarListaNombres();
        agregarNombre($$, $1);
    }
    | lista_variables_bool COMA ID_BOOLEANO
    {
        agregarNombre($1, $3);
        $$ = $1;
    }
    ;

tipo:
    ENTERO {}
    | REAL {}
    | CADENA {}
    | CARACTER {}
    ;

instrucciones:
    instruccion {}
    | instruccion FIN_INSTRUCCION instrucciones {}
    ;

instruccion:
    asignacion {}
    | asignacion_booleana {}
    | estructura_condicional
    {
        backpatch($1, tabla_cuadruplas, siguienteQuad(tabla_cuadruplas));
    }
    | estructura_iterativa
    {
        backpatch($1, tabla_cuadruplas, siguienteQuad(tabla_cuadruplas));
    }
    | CONTINUAR
    {
        Cuadrupla *cuadrupla = crearCuadrupla(OP_SALTO, -1, -1, siguienteQuad(tabla_cuadruplas) + 1, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
    }
    ;

asignacion:
    ID ASIGNACION expresion
    {
        Simbolo *simbolo = buscarSimbolo(tabla_simbolos, $1);
        if (!simbolo)
        {
            fprintf(stderr, "\nVariable '%s' NO declarada (linea %d).\n", $1, yylineno);
            insertarSimbolo(tabla_simbolos, $1, $3.tipo);
            simbolo = tabla_simbolos->ultimo;
        }

        if (simbolo->tipo == $3.tipo)
        {
            Cuadrupla *cuadrupla = crearCuadrupla(OP_ASIGNACION, $3.sid, -1, simbolo->sid, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        }
        else if (simbolo->tipo == TIPO_REAL && $3.tipo == TIPO_ENTERO){
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $3.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            Cuadrupla *cuadrupla2 = crearCuadrupla(OP_ASIGNACION, tabla_simbolos->ultimo_id, -1, simbolo->sid, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla2);
        }
        else {
            fprintf(stderr, "\nError de tipo en la asignación a la variable '%s' de tipo %s, de un valor de tipo %s (linea %d).\n", simbolo->nombre, nombreTipo(simbolo->tipo), nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
    }
    ;

asignacion_booleana:
    ID_BOOLEANO ASIGNACION expresion_booleana
    {
        Simbolo *simbolo = buscarSimbolo(tabla_simbolos, $1);
        if (!simbolo) {
            fprintf(stderr, "\nVariable '%s' NO declarada (linea %d).\n", $1, yylineno);
            insertarSimbolo(tabla_simbolos, $1, TIPO_BOOL);
            simbolo = tabla_simbolos->ultimo;
        }

        backpatch($3.t, tabla_cuadruplas, siguienteQuad(tabla_cuadruplas));
        Cuadrupla *cuadrupla_true = crearCuadrupla(OP_ASIGNACION, -1, true, simbolo->sid, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla_true);

        Cuadrupla *goto_continuacion = crearCuadrupla(OP_SALTO, -1, -1, siguienteQuad(tabla_cuadruplas) + 2, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, goto_continuacion);

        backpatch($3.f, tabla_cuadruplas, siguienteQuad(tabla_cuadruplas));
        Cuadrupla *cuadrupla_false = crearCuadrupla(OP_ASIGNACION, -1, false, simbolo->sid, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla_false);
    }
    ;


expresion:
    PARENTESIS_I expresion_sin_parentesis PARENTESIS_D
    {
        $$ = $2;
    }
    | expresion_sin_parentesis
    {
        $$ = $1;
    }
    ;

expresion_sin_parentesis:
    id_o_valor_o_funcion
    {
        $$ = $1;
    }
    | SUMA expresion {
        // Ignorar pero aceptar
        $$.tipo = $2.tipo;
        $$.sid = $2.sid;
    }
    | RESTA expresion {
        if ($2.tipo == TIPO_CADENA || $2.tipo == TIPO_CARACTER) {
            fprintf(stderr, "\nOperación cambio de signo incorrecta para expresión de tipo %s (linea %d).\n", nombreTipo($2.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), $2.tipo);
        Cuadrupla *cuadrupla = crearCuadrupla(OP_CAMBIO_SIGNO, $2.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$.tipo = $2.tipo;
        $$.sid = tabla_simbolos->ultimo_id;
    }
    | expresion SUMA expresion
    {
        int sid1, sid3;
        if ($1.tipo == TIPO_CADENA || $3.tipo == TIPO_CADENA || $1.tipo == TIPO_CARACTER || $3.tipo == TIPO_CARACTER) {
            fprintf(stderr, "\nOperación suma incorrecta para los tipos '%s' y '%s' (linea %d).\n", nombreTipo($1.tipo), nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        else if ($1.tipo == TIPO_REAL && $3.tipo == TIPO_ENTERO) {
            $$.tipo = TIPO_REAL;
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $3.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = $1.sid;
            sid3 = tabla_simbolos->ultimo_id;
        }
        else if ($1.tipo == TIPO_ENTERO && $3.tipo == TIPO_REAL) {
            $$.tipo = TIPO_REAL;
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $1.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = tabla_simbolos->ultimo_id;
            sid3 = $3.sid;
        }
        else {
            $$.tipo = TIPO_ENTERO;
            sid1 = $1.sid;
            sid3 = $3.sid;
        }
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), $$.tipo);
        Cuadrupla *cuadrupla = crearCuadrupla(OP_SUMA, sid1, sid3, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$.sid = tabla_simbolos->ultimo_id;
    }
    | expresion RESTA expresion
    {
        int sid1, sid3;
        if ($1.tipo == TIPO_CADENA || $3.tipo == TIPO_CADENA || $1.tipo == TIPO_CARACTER || $3.tipo == TIPO_CARACTER) {
            fprintf(stderr, "\nOperación resta incorrecta para los tipos '%s' y '%s' (linea %d).\n", nombreTipo($1.tipo), nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        else if ($1.tipo == TIPO_REAL && $3.tipo == TIPO_ENTERO) {
            $$.tipo = TIPO_REAL;
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $3.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = $1.sid;
            sid3 = tabla_simbolos->ultimo_id;
        }
        else if ($1.tipo == TIPO_ENTERO && $3.tipo == TIPO_REAL) {
            $$.tipo = TIPO_REAL;
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $1.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = tabla_simbolos->ultimo_id;
            sid3 = $3.sid;
        }
        else {
            $$.tipo = TIPO_ENTERO;
            sid1 = $1.sid;
            sid3 = $3.sid;
        }
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), $$.tipo);
        Cuadrupla *cuadrupla = crearCuadrupla(OP_RESTA, sid1, sid3, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$.sid = tabla_simbolos->ultimo_id;
    }
    | expresion DIVISION expresion
    {
        if ($1.tipo == TIPO_CADENA || $3.tipo == TIPO_CADENA || $1.tipo == TIPO_CARACTER || $3.tipo == TIPO_CARACTER) {
            fprintf(stderr, "\nOperación división incorrecta para los tipos '%s' y '%s' (linea %d).\n", nombreTipo($1.tipo), nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        Cuadrupla *cuadrupla;
        int sid1, sid3;
        if ($1.tipo == TIPO_REAL && $3.tipo == TIPO_ENTERO) {
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $3.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = $1.sid;
            sid3 = tabla_simbolos->ultimo_id;
        }
        else if ($1.tipo == TIPO_ENTERO && $3.tipo == TIPO_REAL) {
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $1.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = tabla_simbolos->ultimo_id;
            sid3 = $3.sid;
        }
        else {
            sid1 = $1.sid;
            sid3 = $3.sid;
        }
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);

        if ($2 == TIPO_DIVISION_ENTERA) {
            cuadrupla = crearCuadrupla(OP_DIVISION_ENTERA, sid1, sid3, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            $$.tipo = TIPO_ENTERO;
        }
        else if ($2 == TIPO_DIVISION_REAL) { 
            cuadrupla = crearCuadrupla(OP_DIVISION_REAL, sid1, sid3, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            $$.tipo = TIPO_REAL;
        }
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$.sid = tabla_simbolos->ultimo_id;
    }
    | expresion RESTO expresion
    {
        if ($1.tipo == TIPO_CADENA || $3.tipo == TIPO_CADENA || $1.tipo == TIPO_CARACTER || $3.tipo == TIPO_CARACTER) {
            fprintf(stderr, "\nOperación resto incorrecta para los tipos '%s' y '%s' (linea %d).\n", nombreTipo($1.tipo), nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        else if ($1.tipo == TIPO_REAL || $3.tipo == TIPO_REAL) {
            fprintf(stderr, "\nOperación resto inválida para valores reales (linea %d).\n", yylineno);
            exit(EXIT_FAILURE);
        }
        $$.tipo = TIPO_ENTERO;
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), $$.tipo);
        Cuadrupla *cuadrupla = crearCuadrupla(OP_RESTO, $1.sid, $3.sid, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$.sid = tabla_simbolos->ultimo_id;
    }
    | expresion MULTIPLICACION expresion
    {
        int sid1, sid3;
        if ($1.tipo == TIPO_CADENA || $3.tipo == TIPO_CADENA || $1.tipo == TIPO_CARACTER || $3.tipo == TIPO_CARACTER) {
            fprintf(stderr, "\nOperación multiplicación incorrecta para los tipos '%s' y '%s' (linea %d).\n", nombreTipo($1.tipo), nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        else if ($1.tipo == TIPO_REAL && $3.tipo == TIPO_ENTERO) {
            $$.tipo = TIPO_REAL;
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $3.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = $1.sid;
            sid3 = tabla_simbolos->ultimo_id;
        }
        else if ($1.tipo == TIPO_ENTERO && $3.tipo == TIPO_REAL) {
            $$.tipo = TIPO_REAL;
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $1.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = tabla_simbolos->ultimo_id;
            sid3 = $3.sid;
        }
        else {
            $$.tipo = TIPO_ENTERO;
            sid1 = $1.sid;
            sid3 = $3.sid;
        }
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), $$.tipo);
        Cuadrupla *cuadrupla = crearCuadrupla(OP_MULTIPLICACION, sid1, sid3, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$.sid = tabla_simbolos->ultimo_id;
    }
    ;

id_o_valor_o_funcion:
    ID
    {
        Simbolo *simbolo = buscarSimbolo(tabla_simbolos, $1);
        if (!simbolo) {
            fprintf(stderr, "\nVariable '%s' NO declarada (linea %d).\n", $1, yylineno);
            exit(EXIT_FAILURE);
        }
        $$.tipo = simbolo->tipo;
        $$.sid = simbolo->sid;
    }
    | VALOR 
    {
        insertarSimbolo(tabla_simbolos, NULL, $1.tipo);
        Simbolo *simbolo = tabla_simbolos->ultimo;
        if ($1.tipo == TIPO_ENTERO) 
        {
            insertarValor(lista_valores, simbolo->sid, $1.tipo, &$1.entero);
        }
        else if ($1.tipo == TIPO_REAL) 
        {
            insertarValor(lista_valores, simbolo->sid, $1.tipo, &$1.real);
        }
        else if ($1.tipo == TIPO_CARACTER) 
        {
            insertarValor(lista_valores, simbolo->sid, $1.tipo, &$1.caracter);
        }
        else 
        {
            insertarValor(lista_valores, simbolo->sid, $1.tipo, $1.cadena);
        }
        $$.tipo = simbolo->tipo;
        $$.sid = simbolo->sid;
    }
    | llamada_funcion {}
    | ID CORCHETE_I id_o_valor_o_funcion CORCHETE_D {}
    ;

llamada_funcion:
    ID PARENTESIS_I lista_valores PARENTESIS_D {}
    ; 

lista_valores:
    id_o_valor_o_funcion {}
    | lista_valores COMA id_o_valor_o_funcion {}
    | valores_booleanos {}
    | lista_valores COMA valores_booleanos {}
    ; 

id_o_valor_o_funcion_booleana:
    llamada_funcion_booleana {}
    | ID_BOOLEANO CORCHETE_I id_o_valor_o_funcion CORCHETE_D {}
    | valores_booleanos 
    {
        $$ = $1;
    }
    ; 

llamada_funcion_booleana:
    ID_BOOLEANO PARENTESIS_I lista_valores PARENTESIS_D {}
    ; 

valores_booleanos:
    ID_BOOLEANO 
    {
        Simbolo *simbolo = buscarSimbolo(tabla_simbolos, $1);
        if (!simbolo) {
            fprintf(stderr, "\nVariable '%s' NO declarada (linea %d).\n", $1, yylineno);
            exit(EXIT_FAILURE);
        }
        $$ = simbolo->sid;
    }
    | VALOR_BOOL 
    {
        insertarSimbolo(tabla_simbolos, NULL, $1.tipo);
        Simbolo *simbolo = tabla_simbolos->ultimo;
        insertarValor(lista_valores, simbolo->sid, $1.tipo, $1.cadena);
        $$ = simbolo->sid;
    }
    ;

estructura_condicional:
    SI expresion_booleana THEN m instrucciones estructura_sino FSI
    {
        backpatch($2.t, tabla_cuadruplas, $4);

        if ($6.next != NULL)
        {
            backpatch($2.f, tabla_cuadruplas, $6.quad);
            $$ = $6.next;
        }
        else if ($6.next == NULL)
        {
            $$ = $2.f;
        }
    }
    ;

estructura_sino:
    /* Caso base: no hay más `elseif` */
    {
        $$.next = NULL;
    }
    | n SINO m expresion_booleana THEN m instrucciones estructura_sino
    {
        backpatch($4.t, tabla_cuadruplas, $6);

        if ($8.next != NULL)
        {
            backpatch($4.f, tabla_cuadruplas, $8.quad);
            $$.next = $8.next;
        }
        else
        {
            $$.next = $4.f;
        }
        $$.next = unirListas($$.next, $1);
        $$.quad = $3;
    }
    ;

n:
    {
        $$ = crearLista(siguienteQuad(tabla_cuadruplas));
        Cuadrupla *cuadrupla = crearCuadrupla(OP_SALTO, -1, -1, -1, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
    }

expresion_booleana:
    id_o_valor_o_funcion_booleana 
    {
        $$.t = crearLista(siguienteQuad(tabla_cuadruplas)); 
        $$.f = crearLista(siguienteQuad(tabla_cuadruplas) + 1); 
        Cuadrupla *cuadrupla = crearCuadrupla(OP_SALTO_CONDICIONAL, $1, -1, -1, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        Cuadrupla *cuadrupla2 = crearCuadrupla(OP_SALTO, -1, -1, -1, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla2);
    }
    | id_o_valor_o_funcion COMPARADOR id_o_valor_o_funcion 
    {
        int sid1, sid3;
        if ($1.tipo == TIPO_CADENA || $3.tipo == TIPO_CADENA || $1.tipo == TIPO_CARACTER || $3.tipo == TIPO_CARACTER) 
        {
            if ($3.tipo != $1.tipo || ($2 != TIPO_COMPARADOR_IGUAL && $2 != TIPO_COMPARADOR_DISTINTO)) 
            {
                fprintf(stderr, "\nOperación comparación incorrecta (%s) para los tipos '%s' y '%s' (linea %d).\n", signoComparacion($2), nombreTipo($1.tipo), nombreTipo($3.tipo), yylineno);
                exit(EXIT_FAILURE);
            }
            sid1 = $1.sid;
            sid3 = $3.sid;
        }
        else if ($1.tipo == TIPO_REAL && $3.tipo == TIPO_ENTERO) 
        {
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $3.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = $1.sid;
            sid3 = tabla_simbolos->ultimo_id;
        }
        else if ($1.tipo == TIPO_ENTERO && $3.tipo == TIPO_REAL) 
        {
            insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_REAL);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_INT_TO_FLOAT, $1.sid, -1, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
            sid1 = tabla_simbolos->ultimo_id;
            sid3 = $3.sid;
        }
        else 
        {
            sid1 = $1.sid;
            sid3 = $3.sid;
        }
        $$.t = crearLista(siguienteQuad(tabla_cuadruplas));
        $$.f = crearLista(siguienteQuad(tabla_cuadruplas) + 1);

        // Cuádrupla de salto condicional con el operador de comparación
        Cuadrupla *cuadrupla = crearCuadrupla(OP_SALTO_CONDICIONAL+(100*$2), sid1, sid3, -1, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        Cuadrupla *cuadrupla2 = crearCuadrupla(OP_SALTO, -1, -1, -1, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla2);
    }
    | expresion_booleana OPERADOR m expresion_booleana 
    {
        if ($2 == TIPO_OPERADOR_Y) { 
            backpatch($1.t, tabla_cuadruplas, $3);
            $$.f = unirListas($1.f, $4.f);
            $$.t = $4.t;
        } else { // TIPO_OPERADOR_0
            backpatch($1.f, tabla_cuadruplas, $3);
            $$.t = unirListas($1.t, $4.t);
            $$.f = $4.f;
        }
    }
    | PARENTESIS_I expresion_booleana PARENTESIS_D 
    {
        $$.t = $2.t;
        $$.f = $2.f;
    }
    | NEGACION expresion_booleana
    {
        $$.t = $2.f;
        $$.f = $2.t;
    }
    ;

m: 
    {
        $$ = siguienteQuad(tabla_cuadruplas);
    }
    ;

estructura_iterativa:
    MIENTRAS m expresion_booleana HACER m instrucciones FMIENTRAS 
    {
        backpatch($3.t, tabla_cuadruplas, $5);

        Cuadrupla *cuadrupla = crearCuadrupla(OP_SALTO, -1, -1, $2, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        $$ = $3.f;
    }
    | PARA condicion_para HACER instrucciones FPARA 
    {
        // Enviamos a las capas superiores el salto hacia fuera del bucle
        $$ = $2.next;

        // Recogemos el sid del ID del bucle
        int id_sid = buscarCuadruplaPorQuad(tabla_cuadruplas, $2.quad)->operando1;

        // Generamos el sid para el valor 1
        int aux = 1;
        insertarSimbolo(tabla_simbolos, NULL, TIPO_ENTERO);
        Simbolo *simbolo = tabla_simbolos->ultimo;
        insertarValor(lista_valores, simbolo->sid, TIPO_ENTERO, &aux);

        // Hacemos la asignación intermedia para el incremento de ID
        insertarSimbolo(tabla_simbolos, generarTemporal(tabla_cuadruplas), TIPO_ENTERO);
        Cuadrupla *cuadrupla = crearCuadrupla(OP_SUMA, id_sid, simbolo->sid, tabla_simbolos->ultimo_id, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);

        // Asignamos el incremento a ID
        cuadrupla = crearCuadrupla(OP_ASIGNACION, tabla_simbolos->ultimo_id, -1, id_sid, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        
        // Generamos el salto incondicional a la comprobación del bucle
        cuadrupla = crearCuadrupla(OP_SALTO, -1, -1, $2.quad, siguienteQuad(tabla_cuadruplas));
        insertarCuadrupla(tabla_cuadruplas, cuadrupla);
    }
    ;

condicion_para:
    asignacion_entera HASTA expresion
    {
        if ($3.tipo == TIPO_ENTERO)
        {
            // Generamos el salto condicional del bucle
            $$.quad = siguienteQuad(tabla_cuadruplas);
            Cuadrupla *cuadrupla = crearCuadrupla(OP_SALTO_CONDICIONAL+(100*TIPO_COMPARADOR_MENOR_IGUAL), $1, $3.sid, siguienteQuad(tabla_cuadruplas) + 2, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);

            // Generamos el salto incondicional para salir del bucle
            $$.next = crearLista(siguienteQuad(tabla_cuadruplas));
            cuadrupla = crearCuadrupla(OP_SALTO, -1, -1, -1, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        }
        else {
            fprintf(stderr, "\nError de tipo en el bucle PARA. Configurado el hasta con una expresión de tipo %s (linea %d).\n", nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
    }
    ;

asignacion_entera:
    ID ASIGNACION expresion 
    {
        Simbolo *simbolo = buscarSimbolo(tabla_simbolos, $1);
        if (!simbolo)
        {
            fprintf(stderr, "\nVariable '%s' NO declarada (linea %d).\n", $1, yylineno);
            insertarSimbolo(tabla_simbolos, $1, TIPO_ENTERO);
            simbolo = tabla_simbolos->ultimo;
        }

        if ($3.tipo == TIPO_ENTERO)
        {
            // Generamos la asignación inicial del ID para el bucle
            Cuadrupla *cuadrupla = crearCuadrupla(OP_ASIGNACION, $3.sid, -1, simbolo->sid, siguienteQuad(tabla_cuadruplas));
            insertarCuadrupla(tabla_cuadruplas, cuadrupla);
        }
        else {
            fprintf(stderr, "\nError de tipo en el bucle PARA. Asignando a '%s' un tipo %s (linea %d).\n", simbolo->nombre, nombreTipo($3.tipo), yylineno);
            exit(EXIT_FAILURE);
        }
        $$ = simbolo->sid;
    }
    ;


%%

void yyerror(const char *s) 
{
    fprintf(stderr, "\nError en la línea %d: %s.\n", yylineno, s);
}

int main() 
{
    tabla_simbolos = inicializarTablaSimbolos();
    tabla_cuadruplas = crearTablaCuadruplas();
    lista_valores = crearListaValores();

    yyparse();

    int extra = 1;

    Simbolo *simbolo = tabla_simbolos->primero;
    while(simbolo) 
    {
        if (simbolo->entrada) 
        {
            fprintf(stdout, "%d input %s\n", extra, simbolo->nombre);
            extra ++;
        }
        simbolo = simbolo->siguiente;
    }

    int linea = 0;
    Cuadrupla *cuadrupla = tabla_cuadruplas->primero;
    while (cuadrupla) 
    {
        linea = cuadrupla->quad;
        if (cuadrupla->operando2 == -1) 
        {
            if (cuadrupla->operando1 == -1) // Salto incondicional
            {
                int resultado = cuadrupla->resultado;
                fprintf(stdout, "%d goto %d\n", linea + extra, resultado + extra);
            }
            else if (cuadrupla->operacion == OP_ASIGNACION) // Asignación normal
            {
                char *resultado = buscarSimboloPorSID(tabla_simbolos, cuadrupla->resultado)->nombre;
                Simbolo *simbolo = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando1);

                char *operando;
                if (simbolo->nombre) operando = simbolo->nombre;
                else operando = recogerValor(lista_valores, simbolo->sid);
                fprintf(stdout, "%d %s := %s\n", linea + extra, resultado, operando);
            }
            else if (cuadrupla->operacion == OP_SALTO_CONDICIONAL) { // Salto condicional variable
                int resultado = cuadrupla->resultado;
                Simbolo *simbolo = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando1);

                char *operando;
                if (simbolo->nombre) operando = simbolo->nombre;
                else operando = recogerValor(lista_valores, simbolo->sid);
                fprintf(stdout, "%d if %s goto %d\n", linea + extra, operando, resultado + extra);
            }
            else // Operación unaria
            {
                char *resultado = buscarSimboloPorSID(tabla_simbolos, cuadrupla->resultado)->nombre;
                Simbolo *simbolo = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando1);
                char *operacion = signoOperacion(cuadrupla->operacion);

                char *operando;
                if (simbolo->nombre) operando = simbolo->nombre;
                else operando = recogerValor(lista_valores, simbolo->sid);
                fprintf(stdout, "%d %s := %s %s\n", linea + extra, resultado, operacion, operando);
            }
        }
        else if (cuadrupla->operando1 == -1)
        {
            if (cuadrupla->operacion == OP_ASIGNACION) // Asignación booleana
            {
                char *resultado = buscarSimboloPorSID(tabla_simbolos, cuadrupla->resultado)->nombre;
                char *valor;
                if (cuadrupla->operando2) valor = "true";
                else valor = "false";
                fprintf(stdout, "%d %s := %s\n", linea + extra, resultado, valor);
            }
        }
        else
        {
            if (cuadrupla->operacion > 100) // Salto condicional comparación
            {
                int resultado = cuadrupla->resultado;
                char *operacion = signoComparacion((int)(cuadrupla->operacion / 100));
                Simbolo *simbolo = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando1);
                Simbolo *simbolo2 = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando2);

                char *operando1, *operando2;
                if (simbolo->nombre) operando1 = simbolo->nombre;
                else operando1 = recogerValor(lista_valores, simbolo->sid);
                if (simbolo2->nombre) operando2 = simbolo2->nombre;
                else operando2 = recogerValor(lista_valores, simbolo2->sid);
                fprintf(stdout, "%d if %s %s %s goto %d\n", linea + extra, operando1, operacion, operando2, resultado + extra);
            }
            else // Operación aritmética
            {
                char *resultado = buscarSimboloPorSID(tabla_simbolos, cuadrupla->resultado)->nombre;
                Simbolo *simbolo = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando1);
                Simbolo *simbolo2 = buscarSimboloPorSID(tabla_simbolos, cuadrupla->operando2);
                char *operacion = signoOperacion(cuadrupla->operacion);

                char *operando1, *operando2;
                if (simbolo->nombre) operando1 = simbolo->nombre;
                else operando1 = recogerValor(lista_valores, simbolo->sid);
                if (simbolo2->nombre) operando2 = simbolo2->nombre;
                else operando2 = recogerValor(lista_valores, simbolo2->sid);
                fprintf(stdout, "%d %s := %s %s %s\n", linea + extra, resultado, operando1, operacion, operando2);
            }
        }
        cuadrupla = cuadrupla->siguiente;
    }

    simbolo = tabla_simbolos->primero;
    while(simbolo) 
    {
        if (simbolo->salida) 
        {
            extra ++;
            fprintf(stdout, "%d output %s\n", linea + extra, simbolo->nombre);
        }
        simbolo = simbolo->siguiente;
    }

    liberarTablaSimbolos(tabla_simbolos);
    destruirTablaCuadruplas(tabla_cuadruplas);
    liberarListaValores(lista_valores);
}
