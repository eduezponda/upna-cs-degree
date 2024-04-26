import java.util.Scanner;
public class Asteriscos{
    public static void main(String[] args){

        int numLados, numLineas, i, j;

        Scanner scan = new Scanner(System.in);

        System.out.print("Indique el número de lados de la figura que desea visualizar ( 3 o 4): ");
        numLados = scan.nextInt();
        
        
        System.out.print("Introduzca el número de líneas de la figura: ");
        numLineas = scan.nextInt();
        

		if (numLados == 3)
		{
			for (i = 0; i < numLineas - 1; i = i + 1)
			{
				for (i = 0; i < numLineas; i++) {
                	for (j = i; j < numLineas - 1; j++) {
                    		System.out.print(" ");
                	}
                	for (j = numLineas - i; j <= numLineas; j++) {
                    	System.out.print("* ");
                	}	
                	System.out.print("\n");
            }	}
			
			
		}
		else if (numLados == 4)
		{
			for (i = 0; i < numLineas; i = i + 1)
			{
				for (j = 0; j < numLineas; j = j + 1)
				{
					System.out.print("* ");
				}
				System.out.print("\n");
			}
		}
		else
		{
			System.out.println("El número de lineas es incorrecto");
		}

        



    }
}
