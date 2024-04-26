#ifndef IGL_EEI_3
#define IGL_EEI_3


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdbool.h>


#define BUFFER_SIZE 100


struct column {
    char* huecos;
    int altura;
};

typedef struct column* tablero;


bool setname(char*, FILE*);
void dibujarTablero(tablero, int, int);
bool meterFicha(tablero, int, int, int, char);
void inicializarTablero(tablero*, int, int);
void comprobarSetName(int, char**, FILE*, bool);
void iniciarPartida(FILE*, bool);
bool partida(FILE*, tablero, int, int, bool);
bool realizarTurno(tablero, int, int, FILE*, bool);

#endif
