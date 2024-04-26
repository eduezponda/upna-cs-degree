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
public class Bien 
{
    public static String tipoBien;
    public static Integer valorBien;

    Bien(String tipoBien, Integer valorBien) {
        Bien.tipoBien = tipoBien;
        Bien.valorBien = valorBien;
    }
   
    public void Bien()
    {
        this.tipoBien = "";
        this.valorBien = 0;
    }
    
    
    public String consultarTipoBien()
    {
          return tipoBien;  
    }
    
    public Integer consultarValorBien()
    {
        return valorBien;
    }
}
