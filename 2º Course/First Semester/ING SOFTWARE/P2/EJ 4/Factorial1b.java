import java.util.Scanner;

public class Factorial1b {
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);

		System.out.print("Introduce un número entero para calcular su factorial: ");
		
		int num = scan.nextInt();
		
		CalculaFactorial calcula = new CalculaFactorial(num);
		
		System.out.println("El factorial de " + num + " es " + calcula.factorial);
	}
}
