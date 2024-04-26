//Guillermo Azcona Recari
//PRÁCTICA 3 Casino
//13/10/2021

import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Turno//Pensar
{
	//Definir Variables
	private int sumaDados;
	
	//Turno de una persona (Tirar dados dos veces, sumar los dados, ver si es ojos de tigre)
    public void Turno()
    {
        //Tirar dados de una sola persona
        Dado dado1 = new Dado();
        dado1.tirarDado();        
        System.out.println("            El lanzamiento del dado primer dado ha sido: " + dado1.resultadoDado());
        Dado dado2 = new Dado();
        dado2.tirarDado();
        System.out.println("            El lanzamiento del dado segundo dado ha sido: " + dado2.resultadoDado());
        
        //Dar resultados del turno de una persona
        sumaDados = dado1.resultadoDado() + dado2.resultadoDado();
        
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

