#include <stdio.h>
#include "secuenciaEnteros.h"

void nuevaSecuencia(tipoSecuencia* s)
{
	nuevaPila(&s->pilaIzq);
	nuevaPila(&s->pilaDcha);
}

void insertarDelantePunto(tipoSecuencia* s, tipoElementoPila e)
{
	int x;
	
	if (!(esNulaPila(s->pilaDcha)))
	{
		x = cima(s->pilaDcha);
		desapilar(&s->pilaDcha);
		apilar(&s->pilaDcha, e);
		apilar(&s->pilaDcha, x);
	}
	else
	{
		apilar(&s->pilaDcha, e);
	}
}

void insertarEnPunto(tipoSecuencia* s, tipoElementoPila e) 
{
	apilar(&s->pilaDcha, e);
}

void eliminarEnPunto(tipoSecuencia* s)
{
	if (!(esNulaPila(s->pilaDcha)))
	{
		desapilar(&s->pilaDcha);
	}
	else
	{
		
	}
}

tipoElementoPila consultarEnPunto(tipoSecuencia s)
{
	if (!(esNulaPila(s.pilaDcha)))
	{
		
	}
	else
	{
		printf("No hay punto de interés\n");
	}
	
	return cima(s.pilaDcha);
}

void avanzarPunto (tipoSecuencia* s)
{
	int x;
	
	if (!(esNulaPila(s->pilaDcha)))
	{
		x = cima(s->pilaDcha);
		desapilar(&s->pilaDcha);
		apilar(&s->pilaIzq, x);
	}
	else
	{
		
	}
}

void moverPuntoAlPrincipio (tipoSecuencia* s)
{
	tipoPila p;
	int x;
	nuevaPila(&p);
	
	while (!(esNulaPila(s->pilaIzq)))
	{
		x = cima(s->pilaIzq);
		apilar(&p, x);
		desapilar(&s->pilaIzq);
	}
	if (!(esNulaPila(s->pilaDcha)))
	{
		x = cima(s->pilaDcha);
		apilar(&s->pilaIzq, x);
		desapilar(&s->pilaDcha);
	}
	else
	{
	
	}
	while (!(esNulaPila(p)))
	{
		x = cima(p);
		apilar(&s->pilaIzq, x);
		desapilar(&p);
	}
			
}

bool esPuntoUltimo(tipoSecuencia s)
{
	bool b;
	int x;
	if (!(esNulaPila(s.pilaDcha)))
	{
		x = cima(s.pilaDcha);
		desapilar(&s.pilaDcha);
		b = (esNulaPila(s.pilaDcha));
		apilar(&s.pilaDcha, x);
	}
	else
	{
		b = (esNulaPila(s.pilaDcha));
	}
	return b;
	
}

bool esVaciaSecuencia(tipoSecuencia s)
{
	bool b;
	
	b = ((esNulaPila(s.pilaDcha))&&(esNulaPila(s.pilaIzq)));
	
	return b;
}
