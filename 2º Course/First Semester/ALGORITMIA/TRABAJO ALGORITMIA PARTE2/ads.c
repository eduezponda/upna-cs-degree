#include <math.h>
#include "leerCSV.h"
#include "listaOrdenadaEjemplos.h"


typedef struct DatosClases{
	int numClase0, numClase1;
}datosClases;



int main(void){
	float salario,distancia,edad,compra,genero;
	int i, k, maxEdad, minEdad, maxSalario, minSalario, numElementos;
	Ejemplo ejemplo;
	datosClases datosClases;
	char genero2[6], res;
	tipoLista lista;
	tipoCola mat, matriz;
	celdaCola *aux, *aux2;
	
	
	nuevaLista(&lista);
	
	nuevaMatriz(&mat);
	nuevaMatriz(&matriz);
	
	
	printf("Este programa localiza si un usuario ha realizado una compra en internet\n");
	printf("\nRealizado por: Odei Martínez de Morentin, Helen Portuondo Varona, Eduardo Ezponda Igea y Paula Ruiz de Gopegui Rubio\n");
	
	do{
		//mat[0][0]=1;
		//mat[0][1]=1;
		//mat[0][2]=32;
		//mat[0][3]=94650;
		//mat[0][4]=0;
		//mat[1][0]=2;
		//mat[1][1]=1;
		//mat[1][2]=30;
		//mat[1][3]=135150;
		//mat[1][4]=1;
		//mat[2][0]=3;
		//mat[2][1]=0;
		//mat[2][2]=32;
		//mat[2][3]=48750;
		//mat[2][4]=0;
		leerFichero(&mat);
		printf("\n");
		printf("Original Data:\n");
		printf("\n");
		maxSalario = frente(mat).salario;
		minSalario = frente(mat).salario;
		maxEdad = frente(mat).edad;
		minEdad = frente(mat).edad;
		
		aux = mat.ini;
		
		i = 0;
		
		while (aux != NULL){
			printf("Example %d: ID Usuario %d ", i, (int) aux->elem.codigo);
			if (aux->elem.genero == 1)
			{
				printf("Género Male ");
			}
			else
			{
				printf("Género Female ");
			}
			printf("Edad %d Salario Estimado %d Compra %d\n",(int) aux->elem.edad,(int)aux->elem.salario,(int)aux->elem.clase);
			
			if (aux->elem.salario < minSalario)
			{
				minSalario = aux->elem.salario;
			}
			if (aux->elem.salario > maxSalario)
			{
				maxSalario = aux->elem.salario;
			}
			if (aux->elem.edad < minEdad)
			{
				minEdad = aux->elem.edad;
			}
			if (aux->elem.edad > maxEdad)
			{
				maxEdad = aux->elem.edad;
			}
			aux = aux->sig;
			i++;
		}
		printf("\n");
		printf("\n");
		printf("Normalized Data:\n");
		printf("\n");
		
		aux = mat.ini;
		i = 0;
		
		while (aux != NULL){
			aux->elem.edad = (aux->elem.edad - minEdad)/(maxEdad - minEdad); 
			aux->elem.salario = (aux->elem.salario - minSalario)/(maxSalario - minSalario);
			printf("Example %d: ID Usuario %d ", i, (int) aux->elem.codigo);
			if (aux->elem.genero == 1)
			{
				printf("Género Male");
			}
			else
			{
				printf("Género Female");
			}
			printf(" Edad %f Salario Estimado %f Compra %d\n", aux->elem.edad,aux->elem.salario,(int)aux->elem.clase);
			aux = aux->sig;
			i ++;
		}
		printf("\n");
		
		printf("Introduzca un número entero para k (menor o igual que 400 = tamaño data base): ");
		scanf("%d",&k);
		
		while (k > 400 && k < 1)
		{
			printf("Por favor, vuelva a introducir un número entero para k (menor o igual que 400 = tamaño data base): ");
			scanf("%d",&k);
		}
		
		printf("\nIntroduzca los atributos del usuario; ");
		printf("Género (Male/Female), Edad, Salario Estimado y compra(1/0):\n");
		printf("Género (Male/Female): ");
		scanf("%s", genero2);
		
		while (strcmp(genero2,"Male")!=0 && strcmp(genero2,"Female")!=0)
		{
			printf("Por favor, vuelva a introducir el género (Male/Female): ");
			scanf("%s", genero2);
			
		}
		
		if (strcmp(genero2,"Male")==0)
		{
			genero = 1;
		}
		else 
		{
			genero = 0;
		}
		printf("Edad: ");
		scanf("%f", &edad);
		printf("Salario: ");
		scanf("%f", &salario);
		printf("Predicción compra (1/0): ");
		scanf("%f", &compra);
		
		while (compra != 0 && compra != 1)
		{
			printf("Por favor, vuelva a introducir la predicción de compra (1/0): ");
			scanf("%f", &compra);
		}
		
		
		edad = (edad - minEdad) / (maxEdad - minEdad);
		salario = (salario - minSalario) / (maxSalario - minSalario);
		
		datosClases.numClase0 = 0 ;
		datosClases.numClase1 = 0 ;
		
		aux = mat.ini;
		
		for(i = 0; i < k; i++){
			distancia = (edad - aux->elem.edad)*(edad - aux->elem.edad) + (salario - aux->elem.salario)*(salario - aux->elem.salario);
			if(genero == aux->elem.genero){
				distancia = sqrt(distancia);
			}
			else{
				distancia = sqrt(1 + distancia);
			}
			ejemplo.dist = distancia;
			ejemplo.clase = aux->elem.clase;
			ejemplo.i = i;
			insertar(&lista,ejemplo);
			
			if (ejemplo.clase == 0)
			{
				datosClases.numClase0 = datosClases.numClase0 + 1 ;
			}
			else
			{
				datosClases.numClase1 = datosClases.numClase1 + 1 ;
			}
			
			aux = aux->sig;
		}
		
		for(i = k; i < FILAS_MATRIZ; i++){
		
			distancia = (edad - aux->elem.edad)*(edad - aux->elem.edad) + (salario - aux->elem.salario)*(salario - aux->elem.salario);
			if(genero == aux->elem.genero){
				distancia = sqrt(distancia);
			}
			else{
				distancia = sqrt(1 + distancia);
			}
				
			if(distancia < consultarMayor(lista).dist){
				ejemplo.i = i;
				ejemplo.dist = distancia;
				ejemplo.clase = aux->elem.clase;
				
				if (consultarMayor(lista).clase == 0)
				{
					datosClases.numClase0 = datosClases.numClase0 - 1;
				}
				else
				{
					datosClases.numClase1 = datosClases.numClase1 - 1;
				}
				eliminarMayor(&lista);
				insertar(&lista,ejemplo);
				
				if (ejemplo.clase == 0)
				{
					datosClases.numClase0 = datosClases.numClase0 + 1 ;
				}
				else
				{
					datosClases.numClase1 = datosClases.numClase1 + 1 ;
				}
			}
			aux = aux->sig;
		}
		
		if (datosClases.numClase0 > datosClases.numClase1)
		{
			printf("Ejemplo más cercano %d\n", consultarMenor(lista).i);
			printf("Distancia mínima %f\n", consultarMenor(lista).dist);
			printf("Predicción: compra = 0\n");
			
			if (compra == 0)
			{
				printf("¡Enhorabuena! Has acertado tu predicción\n");
			}
			else
			{
				printf("¡Has fallado la predicción!\n");
			}
			
		}
		else if (datosClases.numClase0 < datosClases.numClase1)
		{
			printf("Ejemplo más cercano %d\n", consultarMenor(lista).i);
			printf("Distancia mínima %f\n", consultarMenor(lista).dist);
			printf("Predicción: compra = 1\n");
			
			if (compra == 1)
			{
				printf("¡Enhorabuena! Has acertado tu predicción\n");
			}
			else
			{
				printf("¡Has fallado la predicción!\n");
			}
		}
		else
		{
			int sumaDist0, sumaDist1;
			
			sumaDist0 = 0;
			sumaDist1 = 0;
			
			while (!esNulaLista(lista))
			{
			
				if (consultarMenor(lista).clase == 0)
				{
					sumaDist0 = sumaDist0 + consultarMenor(lista).dist;
				}
				else
				{
					sumaDist1 = sumaDist1 + consultarMenor(lista).dist;
				}
				eliminarMenor(&lista);
				
			}
			//printear el de menor distancia
			
			if (sumaDist0 > sumaDist1)
			{
				printf("Ejemplo más cercano %d\n", consultarMenor(lista).i);
				printf("Distancia mínima %f\n", consultarMenor(lista).dist);
				printf("Predicción: compra = 0\n");
				
				if (compra == 0)
				{
					printf("¡Enhorabuena! Has acertado tu predicción\n");
				}
				else
				{
					printf("¡Has fallado la predicción!\n");
				}
			}
			else if (sumaDist0 < sumaDist1)
			{
				printf("Ejemplo más cercano %d\n", consultarMenor(lista).i);
				printf("Distancia mínima %f\n", consultarMenor(lista).dist);
				printf("Predicción: compra = 1\n");
				
				if (compra == 1)
				{
					printf("¡Enhorabuena! Has acertado tu predicción\n");
				}
				else
				{
					printf("¡Has fallado la predicción!\n");
				}
			}
			else
			{
				printf("Ejemplo más cercano %d\n", consultarMenor(lista).i);
				printf("Distancia mínima %f\n", consultarMenor(lista).dist);
				printf("Predicción: compra = Tienes las mismas posibilidades de comprar o no comprar\n");
				printf("¡Enhorabuena! Has acertado a medias la predicción\n");
			}
		}
		
		printf("\nA continuación hallaremos la nueva matriz con el algoritmo de Wilson segun la k introducida: %d\n",k);
		
		aux2 = mat.ini;
		numElementos = 0;
		k = k + 1;
		
		int z = 0;
		
		
		for(int j = 0; j < FILAS_MATRIZ; j++)
		{
			vaciarLista(&lista);
			aux = mat.ini;
			if (j == k - 1)
			{
				k = k - 1;
			}
			for(i = 0; i < k; i++){
				if (aux != aux2){
					distancia = (edad - aux->elem.edad)*(edad - aux->elem.edad) + (salario - aux->elem.salario)*(salario - aux->elem.salario);
					if(genero == aux->elem.genero){
						distancia = sqrt(distancia);
					}
					else{
						distancia = sqrt(1 + distancia);
					}
					ejemplo.dist = distancia;
					ejemplo.clase = aux->elem.clase;
					ejemplo.i = i;
					insertar(&lista,ejemplo);
					
					if (ejemplo.clase == 0)
					{
						datosClases.numClase0 = datosClases.numClase0 + 1 ;
					}
					else
					{
						datosClases.numClase1 = datosClases.numClase1 + 1 ;
					}
				}
					
				aux = aux->sig;
			}
			
			for(i = k; i < FILAS_MATRIZ; i++){
				if (aux != aux2){
			
					distancia = (edad - aux->elem.edad)*(edad - aux->elem.edad) + (salario - aux->elem.salario)*(salario - aux->elem.salario);
					if(genero == aux->elem.genero){
						distancia = sqrt(distancia);
					}
					else{
						distancia = sqrt(1 + distancia);
					}
						
					if(distancia < consultarMayor(lista).dist){
						ejemplo.i = i;
						ejemplo.dist = distancia;
						ejemplo.clase = aux->elem.clase;
						
						if (consultarMayor(lista).clase == 0)
						{
							datosClases.numClase0 = datosClases.numClase0 - 1;
						}
						else
						{
							datosClases.numClase1 = datosClases.numClase1 - 1;
						}
						eliminarMayor(&lista);
						insertar(&lista,ejemplo);
						
						if (ejemplo.clase == 0)
						{
							datosClases.numClase0 = datosClases.numClase0 + 1 ;
						}
						else
						{
							datosClases.numClase1 = datosClases.numClase1 + 1 ;
						}
					}
				}
				aux = aux->sig;
			}
			
			if (datosClases.numClase0 > datosClases.numClase1)
			{
				if (aux2->elem.clase != 0)
				{
					
					numElementos = numElementos + 1;
					
					//guardar aux2
				}
				else
				{
					celdaCola *aux3;
					aux3 = malloc(sizeof(celdaCola));
					aux3->elem.codigo = aux2->elem.codigo;
					aux3->elem.clase = aux2->elem.clase;
					aux3->elem.edad = aux2->elem.edad;
					aux3->elem.salario = aux2->elem.salario;
					aux3->elem.genero = aux2->elem.salario;
					encolar(&matriz,aux3);
					
					z++;
				}
				
			}
			else if (datosClases.numClase0 < datosClases.numClase1)
			{
				if (aux2->elem.clase != 1)
				{
					numElementos = numElementos + 1;
					
					//guardar aux2
				}
				else
				{
					celdaCola *aux3;
					aux3 = malloc(sizeof(celdaCola));
					aux3->elem.codigo = aux2->elem.codigo;
					aux3->elem.clase = aux2->elem.clase;
					aux3->elem.edad = aux2->elem.edad;
					aux3->elem.salario = aux2->elem.salario;
					aux3->elem.genero = aux2->elem.salario;
					encolar(&matriz,aux3);
					z++;
				}
			}
			else
			{
				int sumaDist0, sumaDist1;
				
				sumaDist0 = 0;
				sumaDist1 = 0;
				
				while (!esNulaLista(lista))
				{
				
					if (consultarMenor(lista).clase == 0)
					{
						sumaDist0 = sumaDist0 + consultarMenor(lista).dist;
					}
					else
					{
						sumaDist1 = sumaDist1 + consultarMenor(lista).dist;
					}
					eliminarMenor(&lista);
					
				}
				//printear el de menor distancia
				
				if (sumaDist0 > sumaDist1)
				{
					if (aux2->elem.clase != 0)
					{
						numElementos = numElementos + 1;
						
						//guardar aux2
					}
					else
					{
						celdaCola *aux3;
						aux3 = malloc(sizeof(celdaCola));
						aux3->elem.codigo = aux2->elem.codigo;
						aux3->elem.clase = aux2->elem.clase;
						aux3->elem.edad = aux2->elem.edad;
						aux3->elem.salario = aux2->elem.salario;
						aux3->elem.genero = aux2->elem.salario;
						encolar(&matriz,aux3);
						z++;
					}
				}
				else if (sumaDist0 < sumaDist1)
				{
					if (aux2->elem.clase != 0)
					{
						numElementos = numElementos + 1;
						
						//guardar aux2
					}
					else
					{
						celdaCola *aux3;
						aux3 = malloc(sizeof(celdaCola));
						aux3->elem.codigo = aux2->elem.codigo;
						aux3->elem.clase = aux2->elem.clase;
						aux3->elem.edad = aux2->elem.edad;
						aux3->elem.salario = aux2->elem.salario;
						aux3->elem.genero = aux2->elem.salario;
						encolar(&matriz,aux3);
						z++;
					}
				}
			}
			aux2 = aux2->sig;
		}
		printf("El número de elementos eliminados es: %d\n",numElementos);
		vaciarLista(&lista);
		
		printf("La nueva matriz es:\n");
		
		aux = matriz.ini;
		
		for (i = 0; i < z; i++)
		{
			printf("Example %d: ID Usuario %d ", i, (int) aux->elem.codigo);
			if (aux->elem.genero == 1)
			{
				printf("Género Male");
			}
			else
			{
				printf("Género Female");
			}
			printf(" Edad %f Salario Estimado %f Compra %d\n", aux->elem.edad,aux->elem.salario,(int)aux->elem.clase);
		}
		
		
		//printf("\n\n\n\n %d %d %d %d\n", minEdad, maxEdad, minSalario, maxSalario);
		
		printf("Saliendo del programa, pulsa s/S:");
		scanf(" %c", &res);
		
		while (res != 's' && res != 'S')
		{
			printf("Por favor, introduce s/S: \n");
			scanf(" %c", &res);
		}
		

		
	}while(res != 's');
	return 0;
	
}

