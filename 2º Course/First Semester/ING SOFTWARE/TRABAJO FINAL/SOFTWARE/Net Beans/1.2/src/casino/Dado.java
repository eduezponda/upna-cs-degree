/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino;

/**
 *
 * @author gazco
 */
public class Dado 
{
    private int valor;
    
    public Dado() 
    {
        
    }
    public int valor()
    {
        return valor;
    }
    
    public void tirar()
    {
        valor = (int)(Math.random()*6) + 1;
    }

}
