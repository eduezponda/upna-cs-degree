#include <stdbool.h>

typedef int tipoElementoPila;
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

