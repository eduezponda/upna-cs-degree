/*

Programa: pixel_funcion.c
          pixels_generator(): Función externa para la definición de los colores RGB en una imagen de formato BMP
Programa principal main(): bmp_funcion_xymaximobuffer.c

*/

#define DIMENSION   256

//
typedef unsigned char BYTE;

// definición del tipo de cada pixel. Cada pixel son tres bytes . Cada byte es tipo BYTE.
typedef struct
{
        BYTE    b;  
        BYTE    g;
        BYTE    r;
} RGB_data; 

void pixels_generator(unsigned int origen_x, unsigned int origen_y, unsigned int lado, unsigned int proporcion,  RGB_data reg_mem[][DIMENSION])
{
 int i,j;

for (i = origen_x; ((origen_x <= i) && (i < (origen_x+lado-1))); i++)
    {
        for (j = origen_y; ((origen_y <= j) && (j < (origen_y+lado-1))); j++)
        {
	    // intensidad de rojo
            reg_mem[i][j].r = proporcion;
	    // intensidad de verde
            reg_mem[i][j].g = proporcion;
            // intensidad de azul
            reg_mem[i][j].b = proporcion;
        }
    }

}
