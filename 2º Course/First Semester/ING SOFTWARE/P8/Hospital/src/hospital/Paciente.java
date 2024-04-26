/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import java.time.LocalDate;

/**
 *
 * @author alumno
 */
public class Paciente 
{
    public String nombre;
    public String apellido1;
    public int annoNacimiento;
    public String[] sintomas;
    public Paciente() 
    {
        this.nombre = "";
        this.apellido1  = "";
        this.annoNacimiento = 0;
        this.sintomas = new String[0];
    }
    public Paciente(String nombre, String apellido1, Integer annoNacimiento, String[] sintomas) 
    {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.annoNacimiento = annoNacimiento;
        this.sintomas = sintomas;
    }    
    
    public int Edad()
    {
        int edad = LocalDate.now().getYear() - this.annoNacimiento;
        return edad;
    }
    
    public int SintomasCOVID()
    {
        int numeroSintomasCovid = 0;
        for(String sintoma : sintomas)
        {
            if(sintoma.equals("Tos") || sintoma.equals("Fiebre") || sintoma.equals("Cansancio"))
                numeroSintomasCovid++;
        }    
        return numeroSintomasCovid;
    }    
    
    public Boolean requiereAislamiento()
    {
        if(this.annoNacimiento==0) return false;
        return Edad()>=70 && SintomasCOVID()>=2;
    }
    
    public String apellidosComaNombre()
    {
        if (this.apellido1 == "" && this.nombre == "")
        {
            return "Está vacío";
        }
        else 
        {
            return(this.apellido1 + "," + this.nombre);
        }
            
    }



    


}
