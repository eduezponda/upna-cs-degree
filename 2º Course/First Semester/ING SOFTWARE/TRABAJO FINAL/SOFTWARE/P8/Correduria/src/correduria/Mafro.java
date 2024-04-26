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
public class Mafro implements Seguro
{
    private int importe;
    private int comision;
    private Cliente cliente;
    private Bien bien;
    
    public Mafro(Cliente cliente, Bien bien)
    {
        this.cliente = cliente;
        this.bien = bien;
    }


    @Override
    public int calcularImporte(Cliente cliente, Bien bien)
    {
        
        if ((cliente.edad < 20) && (bien.tipoBien.equals("vehiculo")))
        {
            this.importe = Math.floorDiv(bien.valorBien * 5, 100);
        }
        else if ((bien.tipoBien.equals("vivienda")) && bien.valorBien > 200000 && cliente.salarioAnual < 20000)
        {
            this.importe = Math.floorDiv(bien.valorBien * 2, 100);
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
            comision = Math.floorDiv(this.importe * 3, 100);
        }
        return comision;
    }
}
