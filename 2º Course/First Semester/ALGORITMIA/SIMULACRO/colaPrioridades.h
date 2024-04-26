#include <stdbool.h>

typedef int tipoValorElemento;

typedef bool tipoPrioridadElemento;

typedef struct celdaC{
	tipoValorElemento elem;
	tipoPrioridadElemento prioridad;
	struct celdaC *sig;
} celdaCola; 

typedef struct tipoC{
	celdaCola* ini;
	celdaCola* ultimo_prioritario;
	celdaCola* fin;
	int n_prioritarios;
	int n_secundarios;
}tipoCola;

void nuevaCola(tipoCola *);

bool hayPrioritarios(tipoCola);

bool haySecundarios(tipoCola);

bool esNulaCola(tipoCola);

void encolar(tipoCola *, tipoValorElemento, tipoPrioridadElemento);

void desencolar(tipoCola *);

tipoValorElemento frente(tipoCola);

int nPrioritarios(tipoCola);

int nSecundarios(tipoCola);




