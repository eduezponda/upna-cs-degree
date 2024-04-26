/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduría;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class PresentadorAdmision {
    private IVistaAdmision vista;
    private Cliente cliente;
    private Bien bien;
    
    public PresentadorAdmision(IVistaAdmision vista){
       this.vista = vista;
       cliente = new Cliente();
       bien = new Bien();
    }
    
    
    public Cliente registrarCliente(){
        String nombre = this.vista.consultarNombre();
        
        String apellido = this.vista.consultarApellido();
        
        Integer anno = this.vista.consultarAnnoNacimiento();
        
        Integer salarioAnual = this.vista.consultarSalarioAnual();
        
        Cliente cliente =  new Cliente(nombre, apellido, anno, salarioAnual);

        return cliente;
        
    }
    
    public Bien registrarBien(){
        
        String tipoBien = this.vista.consultarTipoBien();
        
        Integer valorBien = this.vista.consultarValorBien(tipoBien);
        
        Bien bien = new Bien(tipoBien, valorBien);
        
        return bien;
    }
    
    public void ofrecerOfertaMásVentajosa(Cliente cliente, Bien bien){
        
        Mafro mafro = new Mafro(cliente, bien);
        LineaIndirecta lineaIndirecta = new LineaIndirecta(cliente, bien);
        Adasles adasles = new Adasles(cliente, bien);
        
        Integer importeM = mafro.calcularImporte(cliente, bien);
        Integer comisionM = mafro.calcularComision(cliente, bien);
        
        Integer importeL = lineaIndirecta.calcularImporte(cliente, bien);
        Integer comisionL = lineaIndirecta.calcularComision(cliente, bien);
        
        Integer importeA = adasles.calcularImporte(cliente, bien);
        Integer comisionA = adasles.calcularComision(cliente, bien);
        

        
        if(importeM <= importeL && importeM <= importeA){
            if(importeM.equals(importeL)){
                if(comisionM > comisionL){
                    this.vista.mostrarEnListado("MAFRO", importeM, comisionM);
                }
                else if(comisionL > comisionM){
                    this.vista.mostrarEnListado("LINEA INDIRECTA", importeL, comisionL);
                }
            }
            if(importeM.equals(importeA)){
                if(comisionM > comisionA){
                    this.vista.mostrarEnListado("MAFRO", importeM, comisionM);
                }
                else if(comisionA > comisionM){
                    this.vista.mostrarEnListado("ADASLES", importeA, comisionA);
                }
            }
            this.vista.mostrarEnListado("MAFRO", importeM, comisionM);
        }
        else if(importeL <= importeM && importeL <= importeA){
           if(importeL.equals(importeM)){
                if(comisionM > comisionL){
                    this.vista.mostrarEnListado("MAFRO", importeM, comisionM);
                }
                else if(comisionL > comisionM){
                    this.vista.mostrarEnListado("LINEA INDIRECTA", importeL, comisionL);
                }
            }
            if(importeL.equals(importeA)){
                if(comisionL > comisionA){
                    this.vista.mostrarEnListado("LINEA INDIRECTA", importeL, comisionL);
                }
                else if(comisionA > comisionL){
                    this.vista.mostrarEnListado("ADASLES", importeA, comisionA);
                }
            }
            this.vista.mostrarEnListado("LINEA INDIRECTA", importeL, comisionL);
        }
        else if(importeA <= importeM && importeA <= importeL){
            if(importeA.equals(importeL)){
                if(comisionA > comisionL){
                    this.vista.mostrarEnListado("ADASLES", importeA, comisionA);
                }
                else if(comisionL > comisionA){
                    this.vista.mostrarEnListado("LINEA INDIRECTA", importeL, comisionL);
                }
            }
            if(importeA.equals(importeM)){
                if(comisionM > comisionA){
                    this.vista.mostrarEnListado("MAFRO", importeL, comisionL);
                }
                else if(comisionA > comisionM){
                    this.vista.mostrarEnListado("ADASLES", importeA, comisionA);
                }
            }
            this.vista.mostrarEnListado("ADASLES", importeA, comisionA);
        }
    }  
}
