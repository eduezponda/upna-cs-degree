//package ofertaventajosa;


public class Mafro {
  private float importe;
  private float comision;

  public Mafro()
  {
    
  }
  
  public void calcularImporte(Cliente cliente)
  {
    int edad = cliente.devolverEdad();
    float valor = cliente.devolverValorBien();
    int salario = cliente.devolverSalario();
    String tipo = cliente.devolverTipoBien();
  
    if (edad < 20 && tipo.equals("Vivienda"))
    {
      importe = 5/100*valor;
    }
    else if (tipo.equals("Vivienda") && valor > 200000 && salario < 20000)
    {
      importe = 2/100*valor;
    }
    else
    {
      importe = 3/100*valor;
    }
    
  }
  
  public void calcularComision()
  {
    if (importe <= 1000)
    {
      comision = 1/100*importe;
    }
    else
    {
      comision = 3/100*importe;
    }
    
  }
  
  public float devolverComision()
  {
    return comision;
  }
  public float devolverImporte()
  {
    return importe;
  }
    
}
