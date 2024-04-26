/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduria;

import java.time.LocalDate;

/**
 *
 * @author Guillermo Azcona
 */
public class Cliente 
{

    /**
     *
     */
    public static String nombre;
    public static String apellido;
    public static int annoNacimiento;
    public static int salarioAnual;
    public static int edad;
    public static int annoActual = 2021;
    
    public Cliente() 
    {
        this.edad = Edad();
        this.annoNacimiento = 0;
        this.salarioAnual = 0;
    }
    public Cliente(String nombre, String apellido, Integer salarioAnual, Integer annoNacimiento) 
    {
        Cliente.nombre = nombre;
        Cliente.apellido = apellido;
        Cliente.annoNacimiento = annoNacimiento;
        Cliente.salarioAnual = salarioAnual;
    }    
    
    public int Edad()
    {
        int edad = LocalDate.now().getYear() - this.annoNacimiento;
        return edad;
    }
    
    public String consultarNombre()
    {
        return nombre;
    }
    
    public String consultarApellido()
    {
        return apellido;
    }
    
    public int consultarEdad()
    {
        return edad;
    }
    
    
}
