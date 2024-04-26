//package ofertaventajosa;

public class Bien {

	  private String tipoBien;
	  private int valor;

    public Bien (String tipo, int valor1)
    {
        tipoBien = tipo;
        valor = valor1;
    }
    
    public String devTipo ()
    {
        return tipoBien;
    }
    
    public int devValor ()
    {
        return valor;
    } 
}
