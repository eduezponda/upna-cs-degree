#include <stdio.h>
#include <stdlib.h>

struct column {
    char* huecos;
    int altura;
};
typedef struct column* tablero;
void inicializarTablero(tablero* tab);
void dibujarTablero(tablero tab);


int main (){
    tablero tab;
    inicializarTablero(&tab);
    dibujarTablero(tab);

    return 0;
}


void inicializarTablero(tablero* tab) {
    int filas = 6;
    int columnas = 7;
    *tab = (tablero)malloc(sizeof(struct column) * columnas);

    for (int j = 0; j < columnas; j++) {
        (*tab)[j].huecos = (char*)malloc(sizeof(char) * filas);

        for (int k = 0; k < filas; k++)
            (*tab)[j].huecos[k] = ' ';

        (*tab)[j].altura = 0;
    }
}

void dibujarTablero(tablero tab)
{
    int i, j;
    int filas = 6;
    int columnas = 7;
    // Dibujar los números de columna
    printf(" ");
    for (i = 0; i < 7; i++) {
        printf(" %d", i);
    }
    printf("\n");

    // Dibujar el tablero
    for (i = 0; i < columnas; i++) {
        printf("|");
        for (j = 0; j < filas; j++) {
            printf(" %c", tab[i].huecos[j]);
        }
        printf(" |\n");
    }

    // Dibujar la base del tablero
    printf("+");
    for (i = 0; i < 7; i++) {
        printf("--");
    }
    printf("+\n");
}
