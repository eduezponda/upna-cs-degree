#include <stdbool.h>

typedef int tipoElementoArbolBusqueda;
typedef struct celdaABB{
	tipoElementoArbolBusqueda elem;
	struct celdaABB* izda;
	struct celdaABB* dcha;
}celdaArbolBusqueda; 
typedef celdaArbolBusqueda* tipoArbolBB;

void nuevoArbolBB(tipoArbolBB *);

void insertar(tipoArbolBB *, tipoElementoArbolBusqueda);

void borrar(tipoArbolBB *, tipoElementoArbolBusqueda);

void mostrarPreorden(tipoArbolBB);

void mostrarInorden(tipoArbolBB);

void mostrarPostorden(tipoArbolBB);

bool esVacio(tipoArbolBB);
