import java.io.*;
public class Dados{
    public static void main(String[] args) throws IOException {

        int num1, num2;

        num1 = (int)(Math.random()*6) + 1;
        num2 = (int)(Math.random()*6) + 1;

        System.out.println("El lanzamiento del primer dado es " + num1);
        System.out.println("El lanzamiento del segundo dado es " + num2);
        System.out.println("El total de los dos lanzamientos es " + (num1 + num2));

        if ((num1 == 1) && (num2 == 1))
        {
            System.out.println("¡Felicidades! Has hecho la jugada ojos de tigre");
        }
        else
        {

        }


    }
}
