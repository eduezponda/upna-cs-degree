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
public class Oferta 
{
    public int importe;
    public int comision;
    public String nombre;

    public Oferta(int importe, int comision, String nombre) {
        this.importe = importe;
        this.comision = comision;
        this.nombre = nombre; //To change body of generated methods, choose Tools | Templates.
    }

    public void Oferta()
    {
        this.importe = 0;
        this.comision = 0;
        this.nombre = "";
    }
    
}
