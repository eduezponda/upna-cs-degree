
#include "pilaArbolDinamica.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

int main(){
	char op[50];
	printf("Introduzca la operacion postfija: \n");
	scanf("%[^\n]",op);
	tipoArbolBin a,aux1,aux2;
	tipoPila p;
	nuevaPila(&p);
	for(int i=0;i<strlen(op);i++){
		if(op[i] =='+'||op[i] == '*' || op[i] == '-' || op[i] == '/')
		{
			
			aux1 = cima(p);
			desapilar(&p);
			aux2 = cima(p);
			desapilar(&p);
			apilar(&p,construir(op[i],aux2,aux1));
		}
		else{
			
			nuevoArbolBin(&a);
			a = construir(op[i],NULL,NULL);
			apilar(&p,a);
		}
	}
	inorden(cima(p));
}
