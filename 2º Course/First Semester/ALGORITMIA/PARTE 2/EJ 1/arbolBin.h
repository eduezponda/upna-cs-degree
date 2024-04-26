#include <stdbool.h>

typedef int tipoElementoArbolBin;
typedef struct celdaABin{
	 tipoElementoArbolBin elem;
	 struct celdaABin* izda;
	 struct celdaABin* dcha;
}celdaArbolBin; 
typedef celdaArbolBin* tipoArbolBin;

void nuevoArbolBin(tipoArbolBin *);

bool esVacio(tipoArbolBin);

tipoArbolBin construir(tipoElementoArbolBin, tipoArbolBin, tipoArbolBin );

tipoElementoArbolBin devolverRaiz(tipoArbolBin);

void preorden(tipoArbolBin);

void inorden(tipoArbolBin);

void postorden(tipoArbolBin);


