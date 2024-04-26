#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include "pilaCaracteresDinamica.h"

void coincide (char x, tipoPila* p, bool* b);

int main ()
{
   	int i;
    char s[30];
    tipoPila p;
    bool b;
    char res;
    
    nuevaPila(&p);

    do
    {

        printf("Introduce la frase ");
        scanf(" %s",s);

        i = 0;
        b = true;
        

        while ((i < strlen(s)) && (b = true))
        {
            coincide(s[i], &p, &b);
            i = i + 1;
        }

        if ((b) && (esNulaPila(p)))
        {
            printf("Los paréntesis están balanceados\n");
        }
        else
        {
            printf("Los paréntesis no están balanceados\n");
            vaciarPila(&p);
        }
        
  	printf("¿Deseas continuar? s/n \n");
  	scanf(" %c",&res);
  	
  	

    }while (res == 's' || res == 'S');

    return 0;
}
void coincide (char x, tipoPila* p, bool* b)
{
	char y;
	
	if ((esNulaPila(*p)))
	{
		if ((x == '}') || (x == ']') || (x == ')'))
		{
			*b = false;
		}
		else if ((x == '{') || (x == '[') || (x == '('))
		{
			apilar(p,x);
		}
		else
		{
		
		}
	}
	else 
	{
		y = cima(*p);
		
		if (y == '{')
		{
			if (x == '}')
			{
				desapilar(p);
			}
			else if ((x == ']') || (x == ')'))
			{
				*b = false;
			}
			else if ((x == '{') || (x == '[') || (x == '('))
			{
				apilar(p,x);
			}
			else
			{
			
			}
		}
		else if (y == '[')
		{
			if (x == ']')
			{
				desapilar(p);
			}
			else if ((x == '}') || (x == ')'))
			{
				*b = false;
			}
			else if ((x == '{') || (x == '[') || (x == '('))
			{
				apilar(p,x);
			}
			else
			{
			
			}
		}
		else //else if (y == '(')
		{
			if (x == ')')
			{
				desapilar(p);
			}
			else if ((x == '}') || (x == ']'))
			{
				*b = false;
			}
			else if ((x == '{') || (x == '[') || (x == '('))
			{
				apilar(p,x);
			}
			else
			{
			
			}
		}
	}	
}


