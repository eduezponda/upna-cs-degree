import java.lang.*;
import java.util.Scanner;

class Dados
{
    public static void main(String[] args)
    {
        //Variables
        
        int num1, num2, total;
        
        //Números aleatorios
        
        num1 = (int)(Math.random()*6) + 1;
        
        num2 = (int)(Math.random()*6) + 1;
        
        total = num1 + num2;
        
        //Printeo de números
        
        System.out.println("El resultado del primer dado es " + num1 + ".");
        
        System.out.println("El resultado del segundo dado es " + num2 + ".");
        
        System.out.println("El total de ambos lanzamientos es " + total + ".");
        
        //Felicitación por ojos de tigre
        
        if (total == 2)
        {
            System.out.println("Felicidades! En ambos dados ha salido como resultado el UNO");
        }
    }
}
