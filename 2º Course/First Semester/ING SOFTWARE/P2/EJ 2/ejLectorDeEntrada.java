/**
* Esta clase es un ejemplo de cómo leer datos
* de la entrada estándar
*/
import java.util.Scanner;
public class ejLectorDeEntrada{
    public static void main(String[] args){
        /* Instanciamos un objeto Scanner conectado con
        * la entrada standard */

        Scanner scan = new Scanner(System.in);
        System.out.print("Introduce una cadena de texto: ");

        // Invocamos el método de Scanner para leer

        String linea = scan.nextLine();
        System.out.println("La línea escrita es: " + linea);
        System.out.print(" Introduce un número entero: ");

        // Invocamos el método de Scanner para leer int

        int num = scan.nextInt();
        System.out.println("El número escrito es: " + num);
    }
}
