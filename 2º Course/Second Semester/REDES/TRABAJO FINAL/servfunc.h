#ifndef IGL_EEI_2
#define IGL_EEI_2

#include <unistd.h>
#include <signal.h>
#include "funcpoll.h"


#define BUFFER_SIZE 100


struct column {
    char* huecos;
    int altura;
};

typedef struct column* tablero;


bool opciones(grupo_de_sockets*, tablero, int, int, bool*, int, int, bool);
void generarID (char*);
bool setName(char*, char*);
bool esValidoNombre(char*);
bool registro(FILE*, Cliente*);
bool login(int, Cliente*, FILE* );
bool inicioPartida(grupo_de_sockets*, int, int, bool);
bool meterFicha(tablero, int, int, int, char);
bool realizarTurno(tablero, int, int, char, FILE*, int*);
bool finPartida(tablero, int, int, grupo_de_sockets*, bool, bool);
void inicializarTablero(tablero*, int, int);
void dibujarTablero(tablero, int, int);
int movimientoIA(char, tablero, int, int, grupo_de_sockets);
void deshacerMovimiento(tablero, int);
void liberarTablero(tablero, int, int);

#endif
