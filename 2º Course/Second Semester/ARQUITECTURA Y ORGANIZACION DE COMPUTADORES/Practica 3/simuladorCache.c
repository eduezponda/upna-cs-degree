/*
 * Programa: simuladorCache
 * Nombres: Fermin Sola Bienzobas y Eduardo Ezponda Igea
 * Fecha: 13/03/2023
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

typedef struct CeldaCache{
    long int tag; 
    bool ocupado;
    int antiguedad;
}celdaCache;
/*
celdaCache es el tipo que utilizamos en nuestra caché (matriz).
Utilizamos tag para comprobar si se encuentra la línea en el conjunto de la caché.
Utilizamos ocupado cuando la caché es de reemplazo aleatorio para comprobar si una
celda de la caché está vacía (1 = ocupado, 0 = vacía). 
Utilizamos antiguedad para ver el tiempo que lleva cada línea en la caché (1, asoc)
*/

unsigned long int rangoBits (unsigned long int n, int bitMenor, int bitMayor) {
	unsigned long int ajuste=1, bit2=1, bit1=1;
	if (bitMenor<0 || bitMenor>47) {
		printf("Error en bitMenor: %d\n", bitMenor);
		exit(0);
	}
	else if (bitMayor<0 || bitMayor>47) {
		printf("Error en bitMayor: %d\n", bitMayor);
		exit(0);
	}
	else if (bitMenor >= bitMayor+1) {
		printf("Error en orden.\n");
		exit(0);
	}
	else if(bitMayor-bitMenor+1<48 ) {
		/** calcula valor 2 elevado a bitMenor ***/
		bit1= bit1 << bitMenor;
		/** calcula valor 2 elevado a bitMayor-bitMenor+1 ***/
		bit2= bit2 << ((bitMayor-bitMenor) + 1);
		n =n/bit1;
		n =n%bit2;
	}
	return n;
}

double p(double x,int i) {
    double r = 1.0;
    for(i;i>0;i--) r *= x;
    return r;
}

double logi(int b,double n) {
    double val = 0;
    int i,accurate = 10,reps=0;
    while(n != 1 && accurate>=0) {
        for(i=0;n>=b;i++) n /= b;
            n = p(n,10);
            val = 10*(val+i);
            accurate--; reps++;
    }
    return (double)val/p(10,reps);
}
//Utilizamos la función logi y p para calcular el logaritmo de un número

int main (){
    double tlin, ncon, asoc, reem;
    char linea[100];
    FILE *fichero=fopen("config.txt","r");

    //Capturamos las propiedades de la caché
    fgets(linea,100,fichero);
    sscanf(linea,"Tlin: %lf", &tlin);
    fgets(linea,100,fichero);
    sscanf(linea,"Ncon: %lf", &ncon);
    fgets(linea,100,fichero);
    sscanf(linea,"Asoc: %lf", &asoc);
    fgets(linea,100,fichero);
    sscanf(linea,"Reem: %lf", &reem);
    
    //Calculamos los bits de cada campo de la dirección
    int bits_byte = logi(2,tlin);
    int bits_conjunto = logi(2,ncon);
    int bits_tag = (48-(bits_byte+bits_conjunto));
    printf("bits_byte: %d\n",bits_byte);
    printf("bits_conjunto: %d\n",bits_conjunto);
    printf("bits_tag: %d\n\n",bits_tag);
    
    FILE *f = fopen("traza.txt","r");
    unsigned long int n, conjunto, tag;
    celdaCache cache[(int)ncon][(int)asoc];
    bool esta, libre;
    int i, j, lineaAleatoria, fallos = 0, contador = 0;
    
    //fallos es un contador con los fallos, y contador es una variable con el número de peticiones (direcciones).
    
    
    /*Inicializamos la caché:
    El tag lo inicializamos a -1 para asegurarnos que al comparar tags no coincida.
    Ocupado lo inicializamos a 0 porque la caché al principio está vacía.
    Antiguedad lo inicializamos entre asoc y 1, teniendo la primera línea el valor
    máximo (la línea que más tiempo lleva), y la última el mínimo (1).
    */
    for (i = 0; i < ncon; i++)
    {
        for (j = 0; j < asoc; j++)
        {
            cache[i][j].tag = -1;
            cache[i][j].ocupado = 0;
            cache[i][j].antiguedad = asoc - j;
        }
    }
    
    //Recorremos traza.txt, y vamos introduciendo las líneas en la caché a medida que leemos.
    
    while (fscanf(f, "%lx", &n) != -1) { 
        //Calculamos el tag y el conjunto de la dirección.
        tag = rangoBits(n,bits_byte+bits_conjunto, 47);
        conjunto = rangoBits(n,bits_byte,bits_byte+bits_conjunto-1);
        
        /*printf("byte: %lu\n", rangoBits(n,0,bits_byte-1));
        printf("conjunto: %lu\n", conjunto);
        printf("tag: %lu\n", tag);*/
        
        //Esta variable nos indica si la línea está cargada en la caché.
        esta = false;
        
        for (i = 0; i < asoc; i++)
        {
          if (cache[conjunto][i].tag == tag) //la línea está en la caché
          {
            esta = true;
            break;
          }
        }
        if (!esta){ //si la línea no está cargada... se produce un fallo
            fallos++;
            if (reem) //ALGORITMO de reemplazo LRU
            {
                for (i = 0; i < asoc; i++)
                {
                    /*Buscamos la línea con mayor antiguedad (asoc).
                    Cuando la encontramos, cambiamos su tag y ponemos el de la dirección.
                    También le ponemos la antiguedad a 0, para que a continuación 
                    al aumentar todas las antiguedades, está linea tenga el valor de antiguedad
                    más bajo (1).
                    */
                    if (cache[conjunto][i].antiguedad == asoc)
                    {
                        cache[conjunto][i].tag = tag;
                        cache[conjunto][i].antiguedad = 0;
                        for (j = 0; j < asoc; j++)
                        {
                            cache[conjunto][j].antiguedad ++;
                        }
                        break;
                    }
                }
                
            }
            else //ALGORITMO de reemplazo ALEATORIO
            {
                /*utilizamos libre para ver si hace falta reemplazar alguna línea
                o ya había alguna línea libre.*/
                libre = false;
                for (i = 0; i < asoc; i++)
                {
                  if (cache[conjunto][i].ocupado == 0) 
                  {                                    
                    //si hay una linea libre, introducimos sus datos y la ocupamos.
                    cache[conjunto][i].ocupado = 1;
                    cache[conjunto][i].tag = tag;
                    libre = true;
                    break;
                  }
                }
                if (!libre) 
                {  
                    //si no está libre, calculamos una línea aleatorio entre 0 y asoc - 1,
                    // e introducimos sus datos en dicha línea
                    lineaAleatoria = rand() % (int)asoc;
                    cache[conjunto][lineaAleatoria].tag = tag;
                }
            }
            
        }
        
        contador++; //incrementamos el contador
    }
    
    if (contador == 0)
    {
        printf("La tasa de fallos es 0 % porque no hay ninguna petición en traza.txt\n");
    }
    else
    {
      printf("La tasa de fallos es: %f %\n", (float) fallos/contador * 100);
    }
}

