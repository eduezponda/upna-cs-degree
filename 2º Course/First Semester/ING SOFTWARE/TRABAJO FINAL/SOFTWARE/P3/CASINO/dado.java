import java.lang.*;
import java.io*;
import java.util.Scanner;

package Casino;

class Dado //Lanzarlo y ver el resultado
{
    
    private int numeroDado;
    
    //GENERAR DADOS a través de tirarDado()
    public void tirarDado() 
    {
        numeroDado = (int)(Math.ramdom()*6)+1;
    }
    
    //FUNCION para exportar el resultado de tirarDado()
    public int resultadoDado()
    {
		return numeroDado;
	}
}

