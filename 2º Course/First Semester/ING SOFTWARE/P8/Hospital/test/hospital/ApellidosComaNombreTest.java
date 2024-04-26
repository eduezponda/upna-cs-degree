/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import hospital.Paciente;
import org.junit.Test;

public class ApellidosComaNombreTest 
{
    @Test
    public void Mikel_Martinez()
    {
        Paciente paciente = new Paciente();
        paciente.nombre = "Mikel";
        paciente.apellido1 = "Martinez";
        String apellidoynombre = paciente.apellidosComaNombre();
        //assertTrue(apellidoynombre);

    }
    
    @Test
    public void Sin_nombre_Martinez()
    {
        Paciente paciente = new Paciente();
        paciente.nombre = "";
        paciente.apellido1 = "Martinez";
        String apellidoynombre = paciente.apellidosComaNombre();
        //assertTrue(apellidoynombre);
    }
    
    @Test
    public void Mikel_Sin_Apellido()
    {
        Paciente paciente = new Paciente();
        paciente.nombre = "Mikel";
        paciente.apellido1 = "";
        String apellidoynombre = paciente.apellidosComaNombre();
        //assertTrue(apellidoynombre);
    }
    
    @Test
    public void Sin_Nombre_Sin_Apellido()
    {
        Paciente paciente = new Paciente();
        paciente.nombre = "";
        paciente.apellido1 = "";
        String apellidoynombre = paciente.apellidosComaNombre();
        //assertTrue(apellidoynombre);
    }


}
