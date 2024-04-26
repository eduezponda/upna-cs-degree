import java.util.Scanner;
public class Ejecutora{
	public static void main(String[] args){
	
		Scanner scan = new Scanner(System.in);

		System.out.print("Introduce el número complejo (parte real y parte imaginaria): ");
		
		float r = scan.nextInt();
		float i = scan.nextInt();
		
		NumeroComplejo numeroComplejo = new NumeroComplejo(r,i);
		
		System.out.println("El numero complejo es: " + numeroComplejo.escribirNumeroComplejo() +". El modulo del numero complejo es: " + numeroComplejo.modulo());
		
		System.out.print("Introduce un número complejo para sumar(parte real y parte imaginaria): ");
		int r2 = scan.nextInt();
		int i2 = scan.nextInt();
		NumeroComplejo numeroComplejo2 = new NumeroComplejo(r2,i2);
		System.out.print("Introduce un número complejo para restar(parte real y parte imaginaria): ");
		int r3 = scan.nextInt();
		int i3 = scan.nextInt();
		NumeroComplejo numeroComplejo3 = new NumeroComplejo(r3,i3);
		
		numeroComplejo.suma(numeroComplejo2);
		numeroComplejo.resta(numeroComplejo3);
		
		
		System.out.println("El numero complejo de la operacion es: " + numeroComplejo.escribirNumeroComplejo() +". El modulo del numero complejo es: " + numeroComplejo.modulo());
		
		
		
		
		
	}
}
