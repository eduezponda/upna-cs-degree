#include "leerCSV.h"

void leerFichero(float matriz[FILAS_MATRIZ][COLUMNAS_MATRIZ])
{
    int i = 0;
    FILE *file;
    char buf[BUFFER_SIZE] = {""};
    datos datos;
    
    file = fopen ("Social_Network_Ads.csv", "r");
    fgets(buf, sizeof(buf), file);
    while(!feof(file)){
        fgets(buf, sizeof(buf), file);
        sscanf(buf, "%f,%[^,],%f,%f,%f", &datos.codigo, datos.genero, &datos.edad, &datos.salario, &datos.haComprado);
        matriz[i][0] = datos.codigo;
        matriz[i][2] = datos.edad;
        matriz[i][3] = datos.salario;
        matriz[i][4] = datos.haComprado;
        if(datos.genero == "Female")
            matriz[i][1] = 0;
        else
            matriz[i][1] = 1;
        //printf("%f %f %f %f %f  ",matriz[i][0],matriz[i][1],matriz[i][2],matriz[i][3],matriz[i][4]);
        i++;
    }
    fclose(file);
}
