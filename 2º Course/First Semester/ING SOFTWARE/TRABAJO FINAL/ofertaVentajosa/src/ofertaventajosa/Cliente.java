//package ofertaventajosa;

public class Cliente {

    //private int nacimiento;
	  private int salario;
	  private Bien bien;
	  private int edad;

    public Cliente (int edad1, Bien bien1, int salario1)
    {
        edad = edad1;
        salario = salario1;
        bien = bien1;
    }
    
    public int devolverEdad ()
    {
        return edad;
    }
    
    public String devolverTipoBien()
    {
        return bien.devTipo();
    }
    
    public int devolverValorBien()
    {
        return bien.devValor();
    }
    
    public int devolverSalario()
    {
        return salario;
    }
    
}
