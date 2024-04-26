import java.lang.Math;
public class NumeroComplejo{

	private float real;
	private float imaginaria;
	
	//constructor
	public NumeroComplejo(float preal, float pimaginaria)
	{
		real = preal;
		imaginaria = pimaginaria;
	}
	
	public String escribirNumeroComplejo()
	{
		if (imaginaria>0)
			return ( real + "+" + imaginaria +"i");
		else
			return ( real + imaginaria +"i");
	}
	public double modulo()
	{	
		double modulo;
		
		modulo = Math.sqrt(real*real+imaginaria*imaginaria);
		return modulo;
	}
	public void suma(NumeroComplejo numero){
	
		this.real = real + numero.real;
		this.imaginaria = imaginaria + numero.imaginaria;
	
		
	
	}
	public void resta(NumeroComplejo numero){
		
		this.real = real - numero.real;
		this.imaginaria = imaginaria - numero.real;
	
		
	}
	
}


