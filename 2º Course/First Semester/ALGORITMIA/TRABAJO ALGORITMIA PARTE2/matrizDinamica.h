#include <stdbool.h>
typedef struct ElementoMatriz{
	float codigo, clase, edad, salario, genero;
}elementoMatriz;
typedef elementoMatriz tipoElementoMatriz;
typedef struct celdaC{
	tipoElementoMatriz elem;
	struct celdaC* sig;
} celdaCola; 
typedef struct tipoC{
	celdaCola* ini;
	celdaCola* fin;
}tipoCola;

void nuevaMatriz(tipoCola*);

bool esNulaMatriz(tipoCola);

void encolar(tipoCola*, celdaCola*);

void desencolar(tipoCola*);

tipoElementoMatriz frente(tipoCola);


