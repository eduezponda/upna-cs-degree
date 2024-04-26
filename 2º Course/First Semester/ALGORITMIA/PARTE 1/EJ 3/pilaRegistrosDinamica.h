#include <stdbool.h>
#include <stdio.h>

typedef struct registro
{
    int fecha;
    int numVictimas;
    int fechaUlt;

}Registro;

typedef Registro tipoElementoPila;
typedef struct celdaP{
	tipoElementoPila elem;
	struct celdaP* sig;
} celdaPila; 
typedef celdaPila* tipoPila;

void nuevaPila(tipoPila*);

bool esNulaPila(tipoPila);

void apilar(tipoPila*, tipoElementoPila);

void desapilar(tipoPila*);

tipoElementoPila cima(tipoPila);

