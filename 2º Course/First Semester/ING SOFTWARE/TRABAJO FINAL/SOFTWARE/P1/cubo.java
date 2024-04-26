import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Cubo
{
    public static void main(String[] args)
    {
        //Inicializar las variables
        int numero;
        System.out.println("Introduce un número entero: ");
        //System se importa de java.lang
        
        
        //Leer el teclado e introducirlo en la variable numero
        Scanner reader = new Scanner(System.in);
        numero = 0;
        numero = reader.nextInt();
        
        System.out.println("El cubo de "+ numero + " es "+ numero*numero*numero);
    }
}
