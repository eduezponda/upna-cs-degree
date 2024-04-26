/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hospital;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gazco
 */
public class HospitalTest {
    
    public HospitalTest() {
    }

    @Test
    public void testMain() {
        PresentadorAdmision presentador2 = new PresentadorAdmision(new VistaFalsa("Guille", "Azcona", 1940, new String[]{"Tos", "Fiebre"}));
        presentador2.admitirPacientes();
        presentador2.mostrarPacientesAislamiento();
        assertEquals("Azcona,Guille", VistaFalsa.ultimaLineaEnListado); 
    
    }
}
