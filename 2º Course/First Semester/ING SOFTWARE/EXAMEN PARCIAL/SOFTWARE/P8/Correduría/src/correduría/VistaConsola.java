/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduría;

import java.util.Scanner;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class VistaConsola implements IVistaAdmision{
    private final String ANSI_PURPLE = "\u001B[35m";
    
    private final String ANSI_RESET = "\u001B[0m";
    
    private final Scanner scanner = new Scanner(System.in);
    
    @Override
    
    public String consultarNombre(){
        System.out.print(ANSI_PURPLE + "//Nombre> " + ANSI_RESET);
        
        String nombre = scanner.nextLine();
        
        return nombre;        
    }
    
    @Override
    
    public String consultarApellido(){
        System.out.print(ANSI_PURPLE + "//Apellido> " + ANSI_RESET);
        
        String apellido = scanner.nextLine();
        
        return apellido;
    }
    
    @Override

    public Integer consultarAnnoNacimiento(){
        System.out.print(ANSI_PURPLE + "//Año de Nacimiento> " + ANSI_RESET);
        
        String respuesta = scanner.nextLine();
        
        return Integer.parseInt(respuesta);        
    }

    @Override
    
    public Integer consultarSalarioAnual(){
        System.out.print(ANSI_PURPLE + "//Salario anual> " + ANSI_RESET);
        
        String respuesta = scanner.nextLine();
        
        return Integer.parseInt(respuesta);
    }

    @Override
    
    public String consultarTipoBien(){
        System.out.print(ANSI_PURPLE + "//Tipo del bien> " + ANSI_RESET);
        
        String tipoBien = scanner.nextLine();
        while((!tipoBien.equals("vehiculo")) && (!tipoBien.equals("vivienda"))){
            System.out.println("ERROR: el tipo del bien introducido es incorrecto, introduzca el bien otra vez(vivienda o vehiculo).");
            System.out.print(ANSI_PURPLE + "//Tipo del bien> " + ANSI_RESET);
            tipoBien = scanner.nextLine();
        }
        
        return tipoBien;        
    }
    
    @Override

    public Integer consultarValorBien(String tipoBien){
        System.out.print(ANSI_PURPLE + "//Valor del bien> " + ANSI_RESET);
        
        String respuesta = scanner.nextLine();
        while(tipoBien.equals("vehiculo") && Integer.parseInt(respuesta) >= 50000){
            System.out.println("ERROR: el valor del bien introducido es incorrecto, introduzca el valor otra vez (vehiculo < 50000 y vivienda > 50000)");
            System.out.print(ANSI_PURPLE + "//Valor del bien> " + ANSI_RESET);
            respuesta = scanner.nextLine();
        }
        while(tipoBien.equals("vivienda") && Integer.parseInt(respuesta) <= 50000){
            System.out.println("ERROR: el valor del bien introducido es incorrecto, introduzca el valor otra vez (vehiculo < 50000 y vivienda > 50000)");
            System.out.print(ANSI_PURPLE + "//Valor del bien> " + ANSI_RESET);
            respuesta = scanner.nextLine();
        }
        
        return Integer.parseInt(respuesta);
    }
    
    @Override
    
    public void mostrarEnListado(String nombreS, int importe, int comision){
        System.out.println(nombreS + " | " + importe + " | " + comision);
    }
}
