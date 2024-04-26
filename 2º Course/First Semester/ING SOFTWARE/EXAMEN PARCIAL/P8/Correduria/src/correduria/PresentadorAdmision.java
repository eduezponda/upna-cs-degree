/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduria;

import static correduria.Cliente.nombre;

/**
 *
 * @author Guillermo Azcona
 */
public class PresentadorAdmision 
{
    private IVistaAdmision vista;
    
    Bien bien;
    Cliente cliente;
    Seguro seguro;
    Oferta oferta, mejorOferta;
    
    int importe;
    int comision;
    
    
    private final Seguro seguroArray[];
    private Oferta ofertaArray[];
          
    public PresentadorAdmision(IVistaAdmision vista)
    {
        this.vista = (IVistaAdmision) vista;
        seguroArray = new Seguro[3];
        ofertaArray = new Oferta[3];
        seguroArray[0] = new Adasles(cliente, bien);
        seguroArray[1] = new Mafro(cliente, bien);
        seguroArray[2] = new LineaIndirecta(cliente, bien);
    }
    
    public void admitirCliente()
    {
            String nombre = this.vista.consultarNombre();
            String apellido = this.vista.consultarApellido();
            Integer annoNacimiento = this.vista.consultarAnnoNacimiento();
            Integer salario = this.vista.consultarSalarioAnual();
            cliente = new Cliente(nombre, apellido, annoNacimiento, salario);
    }
    
    public void admitirBien()
    {
        String tipoBien = this.vista.consultarTipoBien();
        Integer valorBien = this.vista.consultarValorBien();
        bien = new Bien(tipoBien, valorBien);
                       
    }
    
    public Oferta consultarSeguro()
    {
        seguroArray[0] = new Adasles(cliente, bien);
        seguroArray[1] = new Mafro(cliente, bien);
        seguroArray[2] = new LineaIndirecta(cliente, bien);
        
        for(int i = 0; i < 3; i++)
        {
            importe = seguroArray[i].calcularImporte(cliente, bien);
            comision = seguroArray[i].calcularComision(cliente, bien);
            ofertaArray[i] = new Oferta(importe, comision, nombre);           
        }
        
        mejorOferta = ofertaArray[0];
        
        for(int i = 0; i < 3; i++)
        {
            if (ofertaArray[i].importe < mejorOferta.importe)
            {
                mejorOferta = ofertaArray[i];
                if (i == 0)
                {
                    mejorOferta.nombre = "Adasles";
                }
                else if (i == 1)
                {
                    mejorOferta.nombre = "Mafro";
                }
                else
                {
                    mejorOferta.nombre = "Linea Indirecta";
                }
            }
            else if (ofertaArray[i].importe == (mejorOferta.importe))
            {
                if (mejorOferta.comision > ofertaArray[i].comision)
                {
                    mejorOferta = ofertaArray[i];
                    if (i == 0)
                    {
                        mejorOferta.nombre = "Adasles";
                    }
                    else if (i == 1)
                    {
                        mejorOferta.nombre = "Mafro";
                    }
                    else
                    {
                        mejorOferta.nombre = "Linea Indirecta";
                    }                    
                }

            }
            
        }
        return mejorOferta;
        
    }
    
    



    void mostrarResultados(Oferta consultarSeguro) {
        System.out.println(mejorOferta.nombre + " || "+ mejorOferta.importe +" || "+ mejorOferta.comision); //To change body of generated methods, choose Tools | Templates.
    }



}