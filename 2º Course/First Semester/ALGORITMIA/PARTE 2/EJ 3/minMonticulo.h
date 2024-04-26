#include <stdbool.h>

typedef int tipoElementoMinMonticulo;

typedef struct tMM{
	tipoElementoMinMonticulo* array;
	int pos;
	int numEl;
}tipoMinMonticulo;

void nuevoMinMonticulo(tipoMinMonticulo*, int);

void insertarMinMonticulo(tipoMinMonticulo *, tipoElementoMinMonticulo);

void eliminarElemento(tipoMinMonticulo *, tipoElementoMinMonticulo);

tipoElementoMinMonticulo devolverRaiz(tipoMinMonticulo);

void mostrarAnchura(tipoMinMonticulo);

bool esVacio(tipoMinMonticulo);

bool estaLleno(tipoMinMonticulo);
