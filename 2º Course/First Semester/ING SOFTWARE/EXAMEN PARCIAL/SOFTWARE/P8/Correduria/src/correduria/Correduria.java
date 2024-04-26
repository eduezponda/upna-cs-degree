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
public class Correduria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaConsola());
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.mostrarResultados(presentador.consultarSeguro());
    }
    
}
