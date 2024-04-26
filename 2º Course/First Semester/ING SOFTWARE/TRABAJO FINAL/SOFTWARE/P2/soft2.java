import java.lang.*;
import java.util.Scanner;

class practica21
{
    public static void main(int[] args)
    {
        
    }
}

class practica22
{
    public static void main(int[] args)
    {
        
        public class EjLectorDeEntrada {
        public static void main(String[] args){
        
        /* Instanciamos un objeto Scanner conectado con
        * la entrada standard */ 
        
        Scanner scan = new Scanner(System.in);
        System.out.print(’’Introduce una cadena de texto: ’’);
        // Invocamos el metodo de Scanner para leer String
        
        String linea = scan.nextLine();
        System.out.println(’’La linea escrita es: ’’ + linea);
        System.out.print(’’Introduce un numero entero: ’’);
        
        // Invocamos el metodo de Scanner para leer int
        
        int num = scan.nextInt();
        System.out.println(’’El numero escrito es: ’’ + num);
    }
}
