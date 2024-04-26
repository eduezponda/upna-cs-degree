//package ofertaventajosa;


public class LineaIndirecta {
  private float importe;
  private float comision;

  public LineaIndirecta()
  {
    
  }
  
  public void calcularImporte(Cliente cliente)
  {
    int edad = cliente.devolverEdad();
    float valor = cliente.devolverValorBien();
    int salario = cliente.devolverSalario();
    String tipo = cliente.devolverTipoBien();
  
    if ((tipo.equals("Vehiculo") && valor < 20000) || (tipo.equals("Vivienda") && valor < 150000))
    {
      importe = 4/100*valor;
    }
    else if (tipo.equals("Vehiculo") && edad > 60)
    {
      importe = 6/100*valor;
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
      comision = 4/100*importe;
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
