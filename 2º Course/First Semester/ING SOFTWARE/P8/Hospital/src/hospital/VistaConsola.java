/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class VistaConsola implements IVistaAdmision
{
    private final String ANSI_PURPLE = "\u001B[35m";
    private final String ANSI_RESET = "\u001B[0m";
    private final Scanner scanner = new Scanner(System.in);
    
    public String consultarNombre() 
    {
        System.out.print(ANSI_PURPLE + "//Nombre> " + ANSI_RESET);
        String nombre = scanner.nextLine();
        return nombre;
    }
    public String consultarApellido() 
    {
        System.out.print(ANSI_PURPLE + "//Apellido> " + ANSI_RESET);
        String apellido = scanner.nextLine();
        return apellido;
    }
    public Integer consultarAnnoNacimiento() 
    {
        System.out.print(ANSI_PURPLE + "//Año de Nacimiento> " + ANSI_RESET);
        String respuesta = scanner.nextLine();
        return Integer.parseInt(respuesta);
    }
    public String[] consultarSintomas()
    {
        System.out.print(ANSI_PURPLE + "//Sintomas> " + ANSI_RESET);
        String respuesta = scanner.nextLine();
        String[] sintomas = respuesta.split(" ");
        return sintomas;
    }
    public Boolean continuaRegistro() 
    {
        System.out.print(ANSI_PURPLE + "//Desea registar paciente (s/n)> " + ANSI_RESET);
        String respuesta = scanner.nextLine();
        return respuesta.equals("S") || respuesta.equals("s");
    }
    public void mostrarEnListado(String linea)
    {
        System.out.println("*   " + linea);
    }
}
