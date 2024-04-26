import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Cubo2
{
    public static void main(String[] args)
    {

        //Lee el primer argumento pasado por la linea de comandos
        int numero = Integer.parseInt(args[0]);
        
        System.out.println("El cubo de "+ numero + " es "+ numero*numero*numero);
    }
}
