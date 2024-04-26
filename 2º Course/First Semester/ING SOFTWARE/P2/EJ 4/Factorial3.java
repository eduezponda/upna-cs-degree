
public class Factorial3{
    public static void main(String[] args){

        int n, fact;

        n = Integer.parseInt(args[0]);

        fact = 1;

    	for (int i = 1; i <= n; i = i + 1)
    	{
    		fact = fact * i;
    	}

    	System.out.println("El factorial de " + n + " es: " + fact);

	}
}
