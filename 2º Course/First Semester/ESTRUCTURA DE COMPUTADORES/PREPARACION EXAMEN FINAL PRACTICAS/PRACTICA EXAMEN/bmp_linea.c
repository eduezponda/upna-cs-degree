
/*
Programa:     	bmp_funcion_arguDimension -> como se pasa la dimension de un array como argumento de una funcion
Descripción:  	Genera un rectángulo en una imagen bitmap en formato BMP y la guarda en el fichero test.bmp.  
Funciones:    	void pixels_generator(unsigned int origen_x, unsigned int origen_y, unsigned int lado, unsigned int proporcion, unsigned int dimension, RGB_data reg_mem[][DIMENSION]); Definida en el módulo pixel_funcion_arguDimension.c ó pixel_funcion_arguDimension.s
                IMPORTANTE: ¿si se pasa dimensión como argumento por qué se define la dimensión del array reg_mem?
                La función está definida en un fichero externo a la función principal main().
		Argumentos:
			x -> origen de coordenadas del primer pixel del bucle para las filas
			y -> origen de coordenadas del primer pixel del bucle para las columnas
			lado ->
			proporcion ->
			dimension -> máxima coordenada del cuadrado en el bucle para filas y columnas. No hace falta si ponemos maximo como top-x
			reg_mem -> array 2D de pixeles de tipo RGB_data 
Compilación: generar el módulo objeto pixel_funcion_arguDimension.o  : gcc -m32 -g -c -o pixel_funcion_arguDimension.o pixel_funcion_arguDimension.c ó as --32 --gstabs -o pixel_funcion_arguDimension.o pixel_funcion_arguDimension.s
             gcc -g -m32 -o bmp bmp_funcion_arguDimension.c pixel_funcion_arguDimension.o
Ejecución:    $./bmp
Visualización: comando "display" -> $display test.bmp -> salir con Ctrl-C

ATENCION: la DIMENSION DEL ARRAY se declara tanto en el fichero con la llamada a la funcion_mod como en el fichero con la definición de la función.He probado a poner diferentes valores a DIMENSION en los dos ficheros (llamada y definición ) y no hay error de compilación ni de ejecución pero el resultado de la ejecución si que cambia.

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>



#define DIMENSION   1024  
#define X1   0
#define Y1   0
#define X2   1000  // la pendiente de la recta (y2-y1)/(x2-x1) ha de ser menor a 1
#define Y2   500 

// Definición de tipos
typedef int LONG;
typedef unsigned char BYTE; // tipo de cada byte de cada pixel. Rango de valores entre 0x00 y 0xFF. Indican intensidad de cada color RGB
typedef unsigned int DWORD;
typedef unsigned short WORD;

// definición del tipo de la cabecera FILE del fichero BMP
typedef struct tagBITMAPFILEHEADER
{
    WORD    bfType; // 2  /* Magic identifier */
    DWORD   bfSize; // 4  /* File size in bytes */
    WORD    bfReserved1; // 2
    WORD    bfReserved2; // 2
    DWORD   bfOffBits; // 4 /* Offset to image data, bytes */ 
} __attribute__((packed)) BITMAPFILEHEADER;

// definición del tipo de la cabecera INFO del fichero BMP
typedef struct tagBITMAPINFOHEADER
{
    DWORD    biSize; // 4 /* Header size in bytes */
    LONG     biWidth; // 4 /* Width of image */
    LONG     biHeight; // 4 /* Height of image */
    WORD     biPlanes; // 2 /* Number of colour planes */
    WORD     biBitCount; // 2 /* Bits per pixel */
    DWORD    biCompress; // 4 /* Compression type */
    DWORD    biSizeImage; // 4 /* Image size in bytes */
    LONG     biXPelsPerMeter; // 4
    LONG     biYPelsPerMeter; // 4 /* Pixels per meter */
    DWORD    biClrUsed; // 4 /* Number of colours */ 
    DWORD    biClrImportant; // 4 /* Important colours */ 
} __attribute__((packed)) BITMAPINFOHEADER;


// definición del tipo de cada pixel. Cada pixel son tres bytes . Cada byte es tipo BYTE.
typedef struct
{
        BYTE    b;  
        BYTE    g;
        BYTE    r;
} RGB_data; 


int bmp_generator(char *filename, int width, int height, BYTE *data)
{
    BITMAPFILEHEADER bmp_head;
    BITMAPINFOHEADER bmp_info;
    int size = width * height * 3;

    bmp_head.bfType = 0x4D42; // 'BM'
    bmp_head.bfSize= size + sizeof(BITMAPFILEHEADER) + sizeof(BITMAPINFOHEADER); // 24 + head + info no quad    
    bmp_head.bfReserved1 = bmp_head.bfReserved2 = 0;
    bmp_head.bfOffBits = bmp_head.bfSize - size;
    // finish the initial of head

    bmp_info.biSize = 40;
    bmp_info.biWidth = width;
    bmp_info.biHeight = height;
    bmp_info.biPlanes = 1;
    bmp_info.biBitCount = 24; // bit(s) per pixel, 24 is true color
    bmp_info.biCompress = 0;
    bmp_info.biSizeImage = size;
    bmp_info.biXPelsPerMeter = 0;
    bmp_info.biYPelsPerMeter = 0;
    bmp_info.biClrUsed = 0 ;
    bmp_info.biClrImportant = 0;
    // finish the initial of infohead;

    // copy the data
    FILE *fp; // declaro stream pointer
    if (!(fp = fopen(filename,"wb"))) return 0; // inicializo stream pointer abriendo el fichero 

    fwrite(&bmp_head, 1, sizeof(BITMAPFILEHEADER), fp); //escritura la cabecera FILE
    fwrite(&bmp_info, 1, sizeof(BITMAPINFOHEADER), fp); //escritura la cabecera INFO
	//escritura de pixels
    fwrite(data, 1, size, fp);// 1ºarg: buffer a escribir , 2ºarg: long en bytes de cada elemento, 3ºarg:nº de elementos, 4ºarg: stream pointer
    fclose(fp);

    return 1;
}

// PROTOTIPO -> seis argumentos -> OBSERVAR QUE UNO DE LOS ARGUMENTOS ES LA DIMENSION DEL ARRAY QUE TAMBIEN ES ARGUMENTO DE LA MISMA FUNCION
void pixels_generator_linea(unsigned int origen_x, unsigned int origen_y, unsigned int lado, unsigned int proporcion, unsigned int dimension, RGB_data reg_mem[][DIMENSION]);


// int main(int argc, char **argv)
// Función de entrada 
void main(void)
{

    RGB_data buffer[DIMENSION][DIMENSION]; // Array de pixels 

    memset(buffer, 0x00, sizeof(buffer)); //  inicializa el buffer con el valor cero. Librería libc.
    // Los colores se expresan en formato RGB donde la intensidad de cada color se codifica con un byte
    // 0x00: ausencia de color ; 0xFF: intensidad máxima
    // La ausencia de los tres colores primarios R=G=B=0x00 es el negro
    // R=G=B=0xFF es el blanco

    
    pixels_generator_linea(X1,Y1,X2,Y2,DIMENSION,buffer);

    bmp_generator("./test.bmp", DIMENSION, DIMENSION, (BYTE*)buffer); // casting a buffer. Definido como RGB_data y pasado como BYTE

    exit (0);
}
