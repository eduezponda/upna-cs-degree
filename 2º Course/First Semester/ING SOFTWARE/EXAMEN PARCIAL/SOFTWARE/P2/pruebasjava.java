import java.lang.*;
import java.util.Scanner;


class PronosticoExamenes {
    public static void main(String[] args)
    {
        String [][] nombres = 
        {
            {"Adriana"},
            {" aprueba", " suspende"},
            {" por poco", " por mucho"}
        };
        int a = (int)(Math.random()*2);
        int b = (int)(Math.random()*2);
        System.out.println(nombres[0][0] + nombres[1][a] + nombres[2][b]);
    }
}


