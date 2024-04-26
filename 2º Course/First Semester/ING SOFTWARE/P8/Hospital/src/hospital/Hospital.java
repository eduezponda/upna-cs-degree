/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

/**
 *
 * @author alumno
 */
public class Hospital {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaConsola());
        presentador.admitirPacientes();
        presentador.mostrarPacientesAislamiento();
    }
    
}
