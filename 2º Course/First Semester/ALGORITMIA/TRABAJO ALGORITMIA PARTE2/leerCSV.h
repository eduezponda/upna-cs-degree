#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "matrizDinamica.h"

#define BUFFER_SIZE 100
#define FILAS_MATRIZ 400
#define COLUMNAS_MATRIZ 5

typedef struct Datos
{
    float codigo;
    char genero[30];
    float edad;
    float salario;
    float haComprado;
}datos;

void leerFichero(tipoCola* mat);
