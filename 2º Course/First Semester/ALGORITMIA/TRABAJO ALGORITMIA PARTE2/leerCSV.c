#include "leerCSV.h"

void leerFichero(tipoCola* mat)
{
    
    FILE *file;
    char buf[BUFFER_SIZE] = {""};
    datos datos;
    
    file = fopen ("Social_Network_Ads.csv", "r");
    fgets(buf, sizeof(buf), file);
    while(!feof(file)){
    	celdaCola* aux;
    	aux = malloc(sizeof(celdaCola));
    
        fgets(buf, sizeof(buf), file);
        sscanf(buf, "%f,%[^,],%f,%f,%f", &datos.codigo, datos.genero, &datos.edad, &datos.salario, &datos.haComprado);
        aux->elem.codigo = datos.codigo;
        aux->elem.edad = datos.edad;
        aux->elem.salario = datos.salario;
        aux->elem.clase = datos.haComprado;
        printf("%s\n",datos.genero);
        if(strcmp(datos.genero,"Female")==0)
            aux->elem.genero = 0;
        else
            aux->elem.genero = 1;
        encolar(mat,aux);
        
        //printf("%f %f %f %f %f  ",matriz[i][0],matriz[i][1],matriz[i][2],matriz[i][3],matriz[i][4]);
        
    }
    fclose(file);
}


