#include <stdio.h>
#include "colaPrioridades.h"

int main (void)
{
	int res, i,  numLote;
	tipoCola c;
	
	i = 0;
	numLote = 1;
	nuevaCola(&c);
	
	
	do
	{
		
		printf("Introduce pedido %d:\n",i + 1);
		printf(" Pulsa 0 para pedido de plata\n");
		printf(" Pulsa 1 para pedido de oro\n");
		printf(" Pulsa 2 para no admitir nuevos pedidos hoy\n");
		printf("Opcion: ");
		scanf("%d",&res);
		
		if (res == 2)
		{
			printf("Se ha producido el lote %d. Los pedidos producidos han sido:\n",numLote);
			numLote = numLote + 1;
				
			while (!esNulaCola(c))
			{
				printf("Pedido %d\n",frente(c));
				desencolar(&c);
			}
		}
		else
		{
			i = i + 1;
			
			if (res == 0)
			{
				
				
				encolar(&c,i,false);
				
			}
			else
			{
				encolar(&c,i,true);
			}
		
			if (c.n_prioritarios  == 3 || c.n_prioritarios + c.n_secundarios == 10)
			{
				printf("Se ha producido el lote %d. Los pedidos producidos han sido:\n",numLote);
				numLote = numLote + 1;
				
				while (!esNulaCola(c))
				{
					printf("Pedido %d\n",frente(c));
					desencolar(&c);
				}
				
				
			}
				
		}
		
		
	
	}while (res == 0 || res == 1);
	
	return 0;
	
}
