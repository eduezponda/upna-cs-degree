//Guillermo Azcona Recari
//PRÁCTICA 4 Casino
//13/10/2021

import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Dado //Lanzarlo y ver el resultado
{
    int numeroDado;
    
    //GENERAR DADOS a través de tirarDado()
    public void lanzar() 
    {
        numeroDado = (int)(Math.random()*6)+1;
    }
    
    //FUNCION para exportar el resultado de tirarDado()
    public int getValor()
    {
		return numeroDado;
	}
}

