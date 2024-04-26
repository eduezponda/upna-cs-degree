import java.util.Scanner;
public class Factorial1{
    public static void main(String[] args){

    	int n, fact;

    	Scanner scan = new Scanner(System.in);

    	System.out.print("Indique un número para el calculo del factorial: ");
    	n = scan.nextInt();
    
    	fact = 1;
    
    	for (int i = 1; i <= n; i = i + 1)
    	{
    		fact = fact * i;
    	}
    	
    	System.out.println("El factorial de " + n + " es: " + fact);
		
	}
}
