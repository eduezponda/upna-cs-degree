//EJERCICIO CAJA COMPARABLE

import java.lang.*;
import java.io*;
import java.util.Scanner;

class Caja
{
    //PROPIEDADES 
    private double largo;
    private double ancho;
    private double alto;
    private int estado;
    
    //CONSTRUIMOS CAJA
    public Caja (double longitud, double anchura, double altura)
    {
        largo = longitud;
        ancho = anchura;
        alto = altura;
        estado = 0;
    }
    
    public interface Comparable
    {
        // Se emplean las siguientes constantes para indicar el
        // resultado de la comparacion
        public static final 
        int MAS PEQUEÑO QUE = -1;
        int MAS GRANDE QUE = 1;
        int IGUAL QUE = 0;
        // El siguiente metodo se emplea para comparar el
        // objeto con el que se invoca contra otro. El
        // resultado indicara si el objeto es menor, mayor
        // o igual que el otro.
        public int esMasGrandeQue(Comparable otro);
    }
}
