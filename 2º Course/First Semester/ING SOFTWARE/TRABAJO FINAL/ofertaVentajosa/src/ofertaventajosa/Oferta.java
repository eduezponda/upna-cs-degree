//package ofertaventajosa;

public class Oferta {
  private float precio;
  private String nombre;
  public Oferta()
  {
  
  }
  
  public void calcularOferta(Cliente cliente, LineaIndirecta lineaIndirecta, Adasles adasles, Mafro mafro)
  {
    float precioMafro = mafro.devolverImporte() + (int)mafro.devolverComision(); //mafro
    float precioLineaIndirecta = lineaIndirecta.devolverImporte() + (int)lineaIndirecta.devolverComision(); //linea indirecta
    float precioAdasles = adasles.devolverImporte() + (int)adasles.devolverComision(); //adasles
    
    if (precioMafro <= precioLineaIndirecta && precioMafro <= precioAdasles)
    {
      if (precioMafro == precioLineaIndirecta && precioMafro == precioAdasles)
      {
        precio = calcularMaximo(mafro.devolverComision(), lineaIndirecta.devolverComision(), adasles.devolverComision());
      }
      else if (precioMafro == precioLineaIndirecta)
      {
        if (mafro.devolverComision() >= lineaIndirecta.devolverComision())
        {
          precio = precioMafro;
          nombre = "MAFRO";
        }
        else
        {
          precio = precioLineaIndirecta;
          nombre = "LINEAINDIRECTA";
        }
      }
      else if (precioMafro == precioAdasles)
      {
        if (mafro.devolverComision() >= adasles.devolverComision())
        {
          precio = precioMafro;
          nombre = "MAFRO";
        }
        else
        {
          precio = precioAdasles;
          nombre = "ADASLES";
        }
      }
      else //caso generico
      {
        precio = precioMafro;
        nombre = "MAFRO";
      }
      
    }
    else if (precioLineaIndirecta <= precioAdasles && precioLineaIndirecta <= precioMafro)
    {
      if (precioMafro == precioLineaIndirecta)
      {
        if (mafro.devolverComision() >= lineaIndirecta.devolverComision())
        {
          precio = precioMafro;
          nombre = "MAFRO";
        }
        else
        {
          precio = precioLineaIndirecta;
          nombre = "LINEAINDIRECTA";
        }
      }
      else if (precioLineaIndirecta == precioAdasles)
      {
        if (lineaIndirecta.devolverComision() >= adasles.devolverComision())
        {
          precio = precioLineaIndirecta;
          nombre = "LINEAINDIRECTA";
        }
        else
        {
          precio = precioAdasles;
          nombre = "ADASLES";
        }
      }
      else //caso generico
      {
        precio = precioLineaIndirecta;
        nombre = "LINEAINDIRECTA";
      }
    }
    else // precioAdasles <= precioMafro && precioAdasles <= precioLineaIndirecta
    {
      if (precioAdasles == precioLineaIndirecta)
      {
        if (adasles.devolverComision() >= lineaIndirecta.devolverComision())
        {
          precio = precioAdasles;
          nombre = "ADASLES";
        }
        else
        {
          precio = precioLineaIndirecta;
          nombre = "LINEAINDIRECTA";
        }
      }
      else if (precioAdasles == precioMafro)
      {
        if (adasles.devolverComision() >= mafro.devolverComision())
        {
          precio = precioAdasles;
          nombre = "ADASLES";
        }
        else
        {
          precio = precioMafro;
          nombre = "MAFRO";
        }
      }
      else //caso generico
      {
        precio = precioAdasles;
        nombre = "ADASLES";
      }
    }
  }

  public float calcularMaximo (float precioMafro, float precioLineaIndirecta, float precioAdasles)
  {
      if (precioMafro >= precioLineaIndirecta && precioMafro >= precioAdasles)
      {
        nombre = "MAFRO";
        return precioMafro;
      }
      else if (precioLineaIndirecta >= precioAdasles && precioLineaIndirecta >= precioMafro)
      {
        nombre = "LINEAINDIRECTA";
        return precioLineaIndirecta;
      }
      else // precioAdasles >= precioMafro && precioAdasles >= precioLineaIndirecta
      {
        nombre = "ADASLES";
        return precioAdasles;
      }
    }
    public float devolverOferta()
    {
      return precio;
    }
    public String devolverNombre()
    {
      return nombre;
    }
}
