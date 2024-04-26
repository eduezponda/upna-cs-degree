/*

Programa: pixel_linea.c
Descripción: algoritmo de  Bresenham: https://en.wikipedia.org/wiki/Bresenham's_line_algorithm -> concepto
                                    : https://www.javatpoint.com/computer-graphics-bresenhams-line-algorithm -> algoritmo
                                    : https://iq.opengenus.org/bresenham-line-drawining-algorithm/ -> pendiente >1
             ejes: x abscisa ( positivo de izda a derecha)  y ordenada (positivo de arriba abajo)
             origen coordenadas : (0,0)(esquina izda abajo)
             algoritmo matemático para rectas con pendiente inferior a la unidad
             pixels_generator_linea(): Función externa para la definición de los colores RGB en una imagen de formato BMP
             Programa principal main(): bmp_linea.c

gcc -m32 -g -c  -o pixel_linea.o pixel_linea.c
 con la opción -c se genera el módulo objeto sin generar el módulo ejecutable

*/



#define DIMENSION  1024 // OJO: es obligatorio que los array multidimensionales tengan definido por lo menos TODAS las dimensiones menos una

//
typedef unsigned char BYTE;

// definición del tipo de cada pixel. Cada pixel son tres bytes . Cada byte es tipo BYTE.
typedef struct
{
        BYTE    b;  
        BYTE    g;
        BYTE    r;
} RGB_data; 

void  pintar(int i,int j,BYTE blue, BYTE green, BYTE red, RGB_data reg_mem[][DIMENSION]);

void pixels_generator_linea(unsigned int x1, unsigned int y1, unsigned int x2, unsigned int y2, unsigned int dimension,  RGB_data reg_mem[][DIMENSION]) {

int i1,i2,dx,dy,d,x,y,xmax;
// las ecuaciones matemáticas está desarrolladas para una recta de pendiente menor que 1
// (x1,y1) y (x2,y2) son los extremos de la recta

dx=x2-x1;
dy = y2-y1;
i1=2*dy;
i2=2*(dy-dx);

  // el extremo inicial (izda) puede ser (x1,y1) ó (x2,y2)
  if (dx<0) {  
    x=x2;
    y=y2;
    xmax=x1;
  }
  else {
    x=x1;
    y=y1;
    xmax=x2;
  } 
     
  d=2*dy-dx; 
  while (x < xmax){
    if (d<0)
      d=d+i1;
    else {
      d=d+i2;
      y=y+1;
    }
    x=x+1;
    // x columnas -> j
    // y filas -> i
    pintar(y,x,0,0,0xFF,reg_mem);
  }
}


void pintar(int i,int j,BYTE blue, BYTE green, BYTE red, RGB_data reg_mem[][DIMENSION]){

 // intensidad de rojo
  reg_mem[i][j].r = red;
  // intensidad de verde
  reg_mem[i][j].g = green;
  // intensidad de azul
  reg_mem[i][j].b = blue;

}

