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
public interface IVistaAdmision 
{
    Boolean continuaRegistro();
    String consultarNombre();
    String consultarApellido();
    Integer consultarAnnoNacimiento();
    String[] consultarSintomas();
    void mostrarEnListado(String linea);
}
