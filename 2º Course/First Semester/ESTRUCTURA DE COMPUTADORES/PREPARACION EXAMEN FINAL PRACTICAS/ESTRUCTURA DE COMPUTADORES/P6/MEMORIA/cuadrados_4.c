/*
Programa:     cuadrados_4.c 
Descripción:  Genera cuatro rectangulos anidados bitmap en formato BMP y guarda la imagen en el fichero test.bmp.  
Compilación:  gcc --32 -o cuadrados_4 cuadrados_4.c
Ejecución:    $./cuadrados_4
Visualización: comando "display" -> $display test.bmp -> salir con Ctrl-C
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>



#define top   512
#define xcoor top/8 // 512/8=64
#define ycoor top/8

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


int bmp_generator(char *filename, int width, int height, unsigned char *data)
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

// Función de entrada 
int main(int argc, char **argv)
{
    int i,j;

    RGB_data buffer[top][top]; // Array de pixels 

    memset(buffer, 0, sizeof(buffer)); //  inicializa el buffer con el valor cero. Librería libc.
    // Los colores se expresan en formato RGB donde la intensidad de cada color se codifica con un byte
    // 0x00: ausencia de color ; 0xFF: intensidad máxima
    // La ausencia de los tres colores primarios R=G=B=0x00 es el negro
    // R=G=B=0xFF es el blanco

    for (i = xcoor; ((xcoor <= i) && (i < (top-xcoor))); i++) //xcorr <= i se podría eliminar ya que no realiza ninguna función
    {
        for (j = ycoor; ((ycoor <= j) && (j < (top-ycoor))); j++)
        {
	    // intensidad de rojo
            buffer[i][j].r = 0xff;
	    // intensidad de verde
            buffer[i][j].g = 0x00;
            // intensidad de azul
            buffer[i][j].b = 0xff;
        }
    }

for (i = 2*xcoor; ((2*xcoor <= i) && (i < (top-2*xcoor))); i++)
    {
        for (j = 2*ycoor; ((2*ycoor <= j) && (j < (top-2*ycoor))); j++)
        {
	    // intensidad de rojo
            buffer[i][j].r = 0x00;
	    // intensidad de verde
            buffer[i][j].g = 0xff;
            // intensidad de azul
            buffer[i][j].b = 0xff;
        }
    }

for (i = 3*xcoor; ((3*xcoor <= i) && (i < (top-3*xcoor))); i++)
    {
        for (j = 3*ycoor; ((3*ycoor <= j) && (j < (top-3*ycoor))); j++)
        {
	    // intensidad de rojo
            buffer[i][j].r = 0xFF;
	    // intensidad de verde
            buffer[i][j].g = 0xff;
            // intensidad de azul
            buffer[i][j].b = 0x00;
        }
    }

    bmp_generator("./test.bmp", top, top, (BYTE*)buffer);    

    return EXIT_SUCCESS;
}
