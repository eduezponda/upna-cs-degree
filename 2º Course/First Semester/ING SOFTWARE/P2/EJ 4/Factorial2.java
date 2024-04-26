import java.io.*;
public class Factorial2{
    public static void main(String[] args) throws IOException {

    	InputStreamReader flujo;
        BufferedReader teclado;
        int n, fact;
        String textoNum;
        
        flujo = new InputStreamReader(System.in);
        teclado = new BufferedReader(flujo);

        System.out.print("Introduce un número entero: ");
        textoNum = teclado.readLine();
        Integer intNum = Integer.valueOf (textoNum);
        n = intNum.intValue();

    	fact = 1;
    
    	for (int i = 1; i <= n; i = i + 1)
    	{
    		fact = fact * i;
    	}
    	

    	System.out.println("El factorial de " + n + " es: " + fact);

	}
}
