package correduría;

import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class Cliente {
    public String nombre;
    
    public String apellido;
    
    public int annoNacimiento;
    
    public int edad;
    
    public int salarioAnual;
    
    public Cliente(){
        this.nombre = "";
        
        this.apellido  = "";
        
        this.annoNacimiento = 0;
        
        this.edad = 0;
        
        this.salarioAnual = 0;
    }
    
    public Cliente(String nombre, String apellido, int annoNacimiento, int salarioAnual){
        this.nombre = nombre;
        
        this.apellido = apellido;
        
        this.annoNacimiento = annoNacimiento;
        
        this.edad = Edad();
        
        this.salarioAnual = salarioAnual;
    }
    
    private int Edad(){
        Integer annoActual = LocalDate.now().getYear();
        return(annoActual - this.annoNacimiento);
    }
   
}

