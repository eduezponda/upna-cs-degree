/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correduria;

/**
 *
 * @author alumno
 */
public interface Seguro 
{
    int calcularImporte(Cliente cliente, Bien bien);
    int calcularComision(Cliente cliente, Bien bien);
}
