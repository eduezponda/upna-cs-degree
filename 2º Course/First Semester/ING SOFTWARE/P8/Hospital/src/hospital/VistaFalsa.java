/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

/**
 *
 * @author alumno
 */
public class VistaFalsa implements IVistaAdmision
{
    private String nombre;
    private String apellido;
    private Integer annoNacimiento; 
    private String[] sintomas;
    public static String ultimaLineaEnListado;
    public VistaFalsa(String nombre, String apellido, Integer annoNacimiento, String[] sintomas)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.annoNacimiento = annoNacimiento;
        this.sintomas = sintomas;
        this.ultimaLineaEnListado = "";
    }

    @Override
    public Boolean continuaRegistro() 
    {
        return false;
    }
    @Override
    public String consultarNombre() 
    {
        return this.nombre;
    }
    @Override
    public String consultarApellido() 
    {
        return this.apellido;
    }
    @Override
    public Integer consultarAnnoNacimiento() 
    {
        return this.annoNacimiento;
    }
    @Override
    public String[] consultarSintomas() 
    {
        return this.sintomas;
    }
    @Override
    public void mostrarEnListado(String linea) 
    {
        this.ultimaLineaEnListado = linea;
    }
}
