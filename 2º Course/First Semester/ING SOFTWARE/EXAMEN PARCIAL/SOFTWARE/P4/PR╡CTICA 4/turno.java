//Guillermo Azcona Recari
//PRÁCTICA 4 Casino
//13/10/2021

import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Turno//Pensar
{
	//Definir Variables
	private Dado dado1, dado2;
	private int sumaDados;
	private String respuesta;
	
	//Turno de una persona (Tirar dados dos veces, sumar los dados, ver si es ojos de tigre)
    public void Turno()
    {
        Scanner reader = new Scanner(System.in);
        System.out.println("        ¿Desea jugar con dado trucado? [s/n]");
        respuesta = reader.nextLine();
        
        if (respuesta.equals("s"))
        {
            dado1 = new DadoTrucado();
            dado2 = new DadoTrucado();
        }
        
        else
        {
            dado1 = new Dado();
            dado2 = new Dado();
        }
        
        //Tirar dados de una sola persona
        dado1.lanzar();        
        System.out.println("            El lanzamiento del dado primer dado ha sido: " + dado1.getValor());
        dado2.lanzar();
        System.out.println("            El lanzamiento del dado segundo dado ha sido: " + dado2.getValor()); 
        
        //Dar resultados del turno de una persona
        sumaDados = dado1.getValor() + dado2.getValor();
        
        //Avisar si es Ojos de Tigre
        if (sumaDados == 2)
        {
			System.out.println("	    ¡Enhorabuena, has conseguido ojos de tigre!");
		}
    }   
    
    //Devolver la suma de los dados
    public int sumaDados()
    {
		return sumaDados;
	}
	
    
}

