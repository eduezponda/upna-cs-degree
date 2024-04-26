package hospital;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alumno
 */
public class TestsAislamiento {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Paciente paciente = new Paciente();
        paciente.annoNacimiento = 1940;
        paciente.sintomas = new String[]{"Tos", "Cansancio"};
        Boolean requiereAislamiento = paciente.requiereAislamiento();
        if(requiereAislamiento) {System.out.println("OK");}
        else {System.out.println("NOK");}
    }
    
}
