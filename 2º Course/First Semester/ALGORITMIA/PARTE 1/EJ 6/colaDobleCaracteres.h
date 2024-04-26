#include <stdbool.h>

typedef char tipoElementoColaDoble;
typedef struct celdaCD{
	tipoElementoColaDoble elem;
	struct celdaCD *ant;
	struct celdaCD *sig;
} celdaColaDoble; 
typedef struct tipoCD{
	celdaColaDoble* ini;
	celdaColaDoble* fin;
}tipoColaDoble;

void nuevaColaDoble(tipoColaDoble *);

void encolarPrimero(tipoColaDoble *, tipoElementoColaDoble);

void encolarUltimo(tipoColaDoble *, tipoElementoColaDoble);

void desencolarPrimero(tipoColaDoble *);

void desencolarUltimo(tipoColaDoble *);

tipoElementoColaDoble elemPrimero(tipoColaDoble);

tipoElementoColaDoble elemUltimo(tipoColaDoble);

bool esNulaColaDoble(tipoColaDoble);
