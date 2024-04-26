//package ofertaventajosa;


public class Adasles {
  private float importe;
  private float comision;
  
  public Adasles()
  {
    
  }
  
  public void calcularImporte(Cliente cliente)
  {
    int edad = cliente.devolverEdad();
    float valor = cliente.devolverValorBien();
    int salario = cliente.devolverSalario(); 
    String tipo = cliente.devolverTipoBien(); 
  
    if (tipo.equals("Vehiculo") && (edad < 20 || edad > 60))
    {
      importe = 6/100*valor;
    }
    else
    {
      importe = 2/100*valor;
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
      comision = 5/100*importe;
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
