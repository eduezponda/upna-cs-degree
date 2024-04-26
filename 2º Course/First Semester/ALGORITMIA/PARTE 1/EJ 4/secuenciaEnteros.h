#include "pilaEnterosDinamica.h"
#include <stdbool.h>

 typedef struct sec{
	 tipoPila pilaIzq;
	 tipoPila pilaDcha;
 } tipoSecuencia; 

void nuevaSecuencia(tipoSecuencia*);

void insertarDelantePunto(tipoSecuencia*, tipoElementoPila);

void insertarEnPunto(tipoSecuencia*, tipoElementoPila);

void eliminarEnPunto(tipoSecuencia*);

tipoElementoPila consultarEnPunto(tipoSecuencia);

void avanzarPunto (tipoSecuencia*);

void moverPuntoAlPrincipio (tipoSecuencia*);

bool esPuntoUltimo(tipoSecuencia);

bool esVaciaSecuencia(tipoSecuencia);
