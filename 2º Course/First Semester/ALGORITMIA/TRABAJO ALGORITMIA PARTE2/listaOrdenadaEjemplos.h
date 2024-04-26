#include <stdbool.h>
typedef struct ejemplo{
	float dist,clase;
	int i;
}Ejemplo;
typedef Ejemplo tipoElementoLista;
typedef struct celdaL{
	tipoElementoLista elem;
	struct celdaL *sig;
	struct celdaL *ant;
}celdaLista; 
typedef struct tipoL{
	celdaLista *ini;
	celdaLista *fin;
}tipoLista;

void nuevaLista(tipoLista *);

void insertar(tipoLista *, tipoElementoLista);

void eliminarMenor(tipoLista *);

void eliminarMayor(tipoLista *);

tipoElementoLista consultarMenor(tipoLista);

tipoElementoLista consultarMayor(tipoLista);

bool esNulaLista(tipoLista);

void imprimirLista (tipoLista);

void vaciarLista(tipoLista *);
