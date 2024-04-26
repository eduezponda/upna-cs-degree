/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduria;

/**
 *
 * @author alumno
 */
public class LineaIndirecta implements Seguro
{
    private int importe;
    private int comision;
    private Cliente cliente;
    private Bien bien;
    
    LineaIndirecta(Cliente cliente, Bien bien)
    {
        this.cliente = cliente;
        this.bien = bien;
    }

    LineaIndirecta() {
        this.cliente = cliente;
        this.bien = bien;//To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int calcularImporte(Cliente cliente, Bien bien)
    {
        
        if ((cliente.edad > 60) && (bien.tipoBien.equals("vehiculo")))
        {
            this.importe = Math.floorDiv(bien.valorBien * 6, 100);
        }
        else if ((bien.tipoBien.equals("vivienda")) && bien.valorBien < 150000)
        {
            this.importe = Math.floorDiv(bien.valorBien * 4, 100);
        }
        else if ((bien.valorBien < 20000 ) && (bien.tipoBien.equals("vehiculo")))
        {
            this.importe = Math.floorDiv(bien.valorBien * 4, 100);
        }
        else 
        {
            this.importe = Math.floorDiv(bien.valorBien * 3, 100);
        }
        return importe;
    }
    
    @Override
    public int calcularComision(Cliente cliente, Bien bien)
    {
        if (this.importe < 1000)
        {
            comision = Math.floorDiv(this.importe * 1, 100);
        }
        else if (this.importe > 1000)
        {
            comision = Math.floorDiv(this.importe * 4, 100);
        }
        return comision;
    }
  
        
}

