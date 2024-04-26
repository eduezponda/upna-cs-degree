/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduria;

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



    @Override
    public String consultarNombre()
    {
        System.out.print(ANSI_PURPLE + "//Nombre> " + ANSI_RESET);
        String nombre = scanner.nextLine();
        return nombre;
    }
    
    @Override
    public String consultarApellido()
    {
        System.out.print(ANSI_PURPLE + "//Apellido> " + ANSI_RESET);
        String apellido = scanner.nextLine();
        return apellido;
    }    
    
    @Override
    public Integer consultarAnnoNacimiento() 
    {
        System.out.print(ANSI_PURPLE + "//Año de Nacimiento> " + ANSI_RESET);
        String respuesta = scanner.nextLine();
        return Integer.parseInt(respuesta);
    }

    @Override
    public Integer consultarSalarioAnual() 
    {
        System.out.print(ANSI_PURPLE + "//Salario Anual> " + ANSI_RESET);
        String salarioAnual = scanner.nextLine();
        return Integer.parseInt(salarioAnual);
    }    
    
    @Override
    public String consultarTipoBien()
    {
        System.out.print(ANSI_PURPLE + "//Tipo de activo> " + ANSI_RESET);
        String tipoBien = scanner.nextLine();
        while (!tipoBien.equals("vivienda") && !tipoBien.equals("vehiculo"))
        {
            System.out.println("ERROR: el tipo de bien introducido es incorrecto, introduzca el bien de huevo (vehiculo o vivienda): ");
            System.out.print (ANSI_PURPLE + "//Tipo del activo> " + ANSI_RESET);
            tipoBien = scanner.nextLine();
        }
        return tipoBien;  

    }
    
    @Override
    public Integer consultarValorBien() 
    {
        System.out.print(ANSI_PURPLE + "//Valor del activo> " + ANSI_RESET);
        String valorBien = scanner.nextLine();
        
        while (!valorBien.equals("vehiculo") && Integer.parseInt(valorBien) >= 50000)
        {
            System.out.println("ERROR: el tipo de bien introducido es incorrecto, introduzca el bien de huevo (vehiculo o vivienda): ");
            System.out.print (ANSI_PURPLE + "//Tipo del activo> " + ANSI_RESET);
            valorBien = scanner.nextLine();
        }
        while (!valorBien.equals("vivienda") && Integer.parseInt(valorBien) <= 50000)
        {
            System.out.println("ERROR: el tipo de bien introducido es incorrecto, introduzca el bien de huevo (vehiculo o vivienda): ");
            System.out.print (ANSI_PURPLE + "//Tipo del activo> " + ANSI_RESET);
            valorBien = scanner.nextLine();
        }
        return Integer.parseInt(valorBien);
    }    

    public void mostrarEnListado(String linea)
    {
        System.out.println("*   " + linea);
    }

}
