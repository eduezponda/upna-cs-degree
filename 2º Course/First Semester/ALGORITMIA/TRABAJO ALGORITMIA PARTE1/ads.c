#include <math.h>
#include "leerCSV.h"

#define N 400
#define COLUMNAS 5

typedef struct ejemplo{
	float dist,clase,compra;
	int i;
}Ejemplo;

int main(void){
	float mat[N][COLUMNAS], salario,distancia,edad,id,compra,genero;
	int i;
	Ejemplo ejemplo;
	char genero2[6], res;
	
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
		leerFichero(mat);
		printf("Original Data:\n");
		
		for(i = 0; i < N; i++){
			printf("Example %d: ID Usuario %d ", i, (int) mat[i][0]);
			if (mat[i][1] == 1)
			{
				printf("Género Male ");
			}
			else
			{
				printf("Género Female ");
			}
			printf("Edad %d Salario Estimado %d Compra %d\n",(int) mat[i][2],(int)mat[i][3],(int)mat[i][4]);
			mat[i][2] = (mat[i][2] - 18)/(60 - 18);
			mat[i][3] = (mat[i][3] - 15000)/(150000 - 15000);
		}
		printf("Normalized Data:\n");
		
		for(i = 0; i < N; i++){
			printf("Example %d: ID Usuario %d ", i, (int) mat[i][0]);
			if (mat[i][1] == 1)
			{
				printf("Género Male");
			}
			else
			{
				printf("Género Female");
			}
			printf(" Edad %f Salario Estimado %f Compra %d\n", mat[i][2],mat[i][3],(int)mat[i][4]);
		}
		
		printf("\nIntroduzca los atributos del usuario.");
		printf("ID Usuario, Género (Male/Female), Edad, Salario Estimado y compra(1/0)\n");
		scanf("%f", &id);
		scanf("%s", genero2);
		scanf("%f", &edad);
		scanf("%f", &salario);
		scanf("%f", &compra);
		
		while (strcmp(genero2,"Male")!=0 && strcmp(genero2,"Female")!=0)
		{
			printf("Por favor, vuelva a introducir el género (Male/Female)\n");
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
		
		
		edad = (edad - 18) / (60 - 18);
		salario = (salario - 15000) / (150000 - 15000);
		
		ejemplo.dist = 0;
		
		for(i = 0; i < N; i++){
		
			distancia = (edad - mat[i][2])*(edad - mat[i][2]) + (salario - mat[i][3])*(salario - mat[i][3]);
			if(genero == mat[i][1]){
				distancia = sqrt(distancia);
			}
			else{
				distancia = sqrt(1 + distancia);
			}
				
			if(distancia < ejemplo.dist || ejemplo.dist == 0){
				ejemplo.i = i;
				ejemplo.dist = distancia;
				ejemplo.clase = mat[i][4];
				if((int)mat[i][4] == (int)compra){
					ejemplo.compra = 1;
				}
				else{
					ejemplo.compra = 0;
				}
			}
			if (ejemplo.dist == 0)
			{
				break;
			}
			
		}
		printf("Ejemplo más cercano %d\n", ejemplo.i);
		printf("Distancia mínima %f\n", ejemplo.dist);
		printf("Predicción: compra = %d\n", (int) ejemplo.clase);
		if ((int)ejemplo.compra == 1)
		{
			printf("Éxito de la predicción: true\n");
		}
		else
		{
			printf("Éxito de la predicción: false\n");
		}
		
		
		printf("¿Deseas continuar? (s/n): ");
		scanf(" %c", &res);
		
	}while(res == 's' || res == 'S');
	return 0;
	
}

