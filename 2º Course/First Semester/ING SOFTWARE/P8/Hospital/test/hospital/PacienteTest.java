/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import hospital.Paciente;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alumno
 */
public class PacienteTest 
{
    
    @Test
    public void Un_paciente_mayor_con_dos_sintomas_covid_requiere_aislamiento()
    {
        // Dado un paciente de más de 70 años que tiene dos sintomas COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{"Tos", "Cansancio"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver cierto
        assertTrue(requiereAislamiento);
    }
    
    @Test
    public void Un_paciente_mayor_con_un_sintoma_covid_no_requiere_aislamiento() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{"Tos", "Diarrea", "Nauseas"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertFalse(requiereAislamiento);
    }
    

    @Test
    public void Un_paciente_mayor_con_dos_sintomas_covid_requiere_aislamiento1() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{"Fiebre", "Cansancio", "Diarrea"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertTrue(requiereAislamiento);
    }
    
    /**
     *
     */
    @Test
    public void Un_paciente_mayor_con_un_sintoma_covid_no_requiere_aislamiento1() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{"Cansancio", "Diarrea"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertFalse(requiereAislamiento);
    }
    
    @Test
    public void Un_paciente_joven_con_un_sintoma_covid_no_requiere_aislamiento() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 2001;
        paciente.sintomas = new String[]{"Tos", "Cansancio"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertFalse(requiereAislamiento);
    }
    
    @Test
    public void Un_paciente_mayor_con_tres_sintomas_covid_si_requiere_aislamiento() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{"Tos", "Cansancio", "Fiebre"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertTrue(requiereAislamiento);
    }
    
    @Test
    public void Un_paciente_sin_edad_con_tres_sintomas_covid_no_requiere_aislamiento() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 0;
        paciente.sintomas = new String[]{"Tos", "Cansancio", "Fiebre"};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertFalse(requiereAislamiento);
    }
    
    @Test
    public void Un_paciente_mayor_sin_sintomas_covid_no_requiere_aislamiento() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertFalse(requiereAislamiento);
    }
    
    @Test
    public void Un_paciente_sin_edad_sin_sintomas_covid_no_requiere_aislamiento() 
    {
        // Dado un paciente de más de 70 años que tiene un sintoma COVID19
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 0;
        paciente.sintomas = new String[]{};
        // Cuando chequeo si requiere o no aislamiento
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        // Entonces debe devolver falso
        assertFalse(requiereAislamiento);
    }
    
}
