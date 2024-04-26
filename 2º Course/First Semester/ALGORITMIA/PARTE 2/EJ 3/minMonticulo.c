#include <stdio.h>
#include "minMonticulo.h"
#include <stdlib.h>

void nuevoMinMonticulo(tipoMinMonticulo* m, int elem)
{
	m->pos = -1;
	m->numEl = elem;
	m->array= malloc(sizeof(tipoElementoMinMonticulo)*elem);
}

void insertarMinMonticulo(tipoMinMonticulo *m, tipoElementoMinMonticulo elem)
{
	
	
	if (!estaLleno(*m))
	{
		int i = m->pos + 1;
		m->pos = m->pos + 1;
		m->array[m->pos] = elem;
		
		while (i > 0 && m->array[(i-1)/2] > elem) // padre i - 1  / 2 ; hijo izda i *2   +1 ; hijo dcha i * 2 + 2
		{
			m->array[i] = m->array[(i-1)/2];
			m->array[(i-1)/2] = elem;
			i = (i - 1) / 2;
		}
		
		
	}
	else
	{
		printf("El montículo está lleno, no puedes insertar ningún elemento\n");
	}
	
}

void eliminarElemento(tipoMinMonticulo *m, tipoElementoMinMonticulo elem)
{
	if (!esVacio(*m))
	{
		int i = 0;
		int aux;
		
		while (i <= m->pos && m->array[i] != elem) // padre i - 1  / 2 ; hijo izda i *2   +1 ; hijo dcha i * 2 + 2
		{
			i = i + 1;
		}
		
		if (m->array[i] == elem)
		{
			m->array[i] = m->array[m->pos];
			m->pos = m->pos - 1;
			
			while ((i * 2 + 1 <= m->pos && m->array[i * 2 + 1] <  m->array[i]) || ((i * 2 + 2) <= m->pos && m->array[i * 2 + 2] < m->array[i]))
			{
				if (i * 2 + 1 <= m->pos && i * 2 + 2 <= m->pos)
				{
					if (m->array[i*2+1] < m->array[i*2+2])
					{
						aux = m->array[i*2+1];
						m->array[i*2+1] = m->array[i];
						m->array[i] = aux;
						i = i*2 + 1;
					}
					else
					{
						aux = m->array[i*2+2];
						m->array[i*2+2] = m->array[i];
						m->array[i] = aux;
						i = i*2 + 2;
					}
				}
				else if (i * 2 + 1 <= m->pos && i * 2 + 2 > m->pos)
				{
					aux = m->array[i*2+1];
					m->array[i*2+1] = m->array[i];
					m->array[i] = aux;
					i = i*2 + 1;
				}
				else 
				{
					aux = m->array[i*2+2];
					m->array[i*2+2] = m->array[i];
					m->array[i] = aux;
					i = i*2 + 2;
				}
			}
		}
		else
		{
		
		}
		
		
	}
	else
	{
		printf("El montículo está vacio, no puedes eliminar ningún elemento\n");
	}
	
	
	
}

tipoElementoMinMonticulo devolverRaiz(tipoMinMonticulo m)
{
	if (esVacio(m))
	{
		printf("Error, el montículo está vacío\n");
		exit(1);
	}
	else
	{
		return (m.array[0]);
	}
}

void mostrarAnchura(tipoMinMonticulo m)
{
	int i = 0;
	
	while (i <= m.pos)
	{
		printf(" %d",m.array[i]);
		i = i + 1;
	}
}

bool esVacio(tipoMinMonticulo m)
{
	return (m.pos == -1);
}

bool estaLleno(tipoMinMonticulo m)
{
	return (m.pos + 1 == m.numEl);
}
