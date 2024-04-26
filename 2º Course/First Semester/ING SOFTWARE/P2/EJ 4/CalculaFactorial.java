public class CalculaFactorial {
	public int factorial = 1;
	
	//constructor
	public CalculaFactorial(int num) {
		for(int i = 1; i <= num; i = i + 1)
		{
			factorial = factorial * i;
		}
	}
}
