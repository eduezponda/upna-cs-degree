/*
Programa:     bitmap_gen_test.c
Descripción:  Genera un imagen bitmap 512x512 (tamaño matriz) en formato BMP (cabecera) y la guarda en el fichero test.bmp. 
Compilación:  gcc --32 -o bitmap_gen_test bitmap_gen_test.c
Ejecución:    $./bitmap_gen_test
Visualización: comando "display" -> $display test.bmp -> salir con Ctrl-C

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Definición de tipos
typedef int LONG;
typedef unsigned char BYTE;
typedef unsigned int DWORD;
typedef unsigned short WORD;



typedef struct tagBITMAPFILEHEADER
{
    WORD    bfType; // 2  /* Magic identifier */
    DWORD   bfSize; // 4  /* File size in bytes */
    WORD    bfReserved1; // 2
    WORD    bfReserved2; // 2
    DWORD   bfOffBits; // 4 /* Offset to image data, bytes */ 
} __attribute__((packed)) BITMAPFILEHEADER;

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

//tipos estructurados para pintar de colores el mapa de bits

/*
typedef struct tagRGBQUAD
{
    unsigned char    rgbBlue;   
    unsigned char    rgbGreen;
    unsigned char    rgbRed;  
    unsigned char    rgbReserved;
} RGBQUAD;
* for biBitCount is 16/24/32, it may be useless
*/

typedef struct
{
        BYTE    b; //blue, green or read
        BYTE    g;
        BYTE    r;
} RGB_data; // RGB TYPE, plz also make sure the order

int bmp_generator(char *filename, int width, int height, unsigned char *data) //dado fichero con un tamaño, nos lo pinta
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
    FILE *fp;
    if (!(fp = fopen(filename,"wb"))) return 0;

    fwrite(&bmp_head, 1, sizeof(BITMAPFILEHEADER), fp);
    fwrite(&bmp_info, 1, sizeof(BITMAPINFOHEADER), fp);
    fwrite(data, 1, size, fp);
    fclose(fp);

    return 1;
}

int main(int argc, char **argv)
{
    int i,j;

    RGB_data buffer[512][512]; //defino buffer de tipo rgb data (3 bytes -> green, red and blue) como matriz[512][512]

    memset(buffer, 0, sizeof(buffer)); //inicializo bytes de buffer a 0. El 0 es un pixel negro

    for (i = 0; i < 256; i++) //recorrer desde abajo hasta la derecha subiendo filas desde la más inferior
    {
        for (j = 0; j < 256; j++)
        {
            buffer[i][j].g = buffer[i][j].b = 0x7f; //0x7f = mitad entre 1 y 0 (blanco y negro -> gris)
            buffer[i][j].r = 0x7f;
            //combinados los 3 colores hacen el color blanco
            //intensidad depende de bit
        }
    }

    bmp_generator("./test.bmp", 512, 512, (BYTE*)buffer);   //me sacas un fichero que empiece en buffer con un 512 de altura y ancho

    return EXIT_SUCCESS;
}
