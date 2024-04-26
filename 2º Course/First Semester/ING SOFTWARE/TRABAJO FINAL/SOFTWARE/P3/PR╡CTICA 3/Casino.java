//Guillermo Azcona Recari
//PRÁCTICA 3 Casino
//13/10/2021

import java.lang.*;
import java.io.*;
import java.util.Scanner;

public class Casino //Main
{
    public static void main(String[] args) throws IOException
    {
		String jugador1, jugador2;
		int ganador;
        //PEDIR NOMBRES
        Scanner reader = new Scanner(System.in);
        System.out.println("Nombre del Jugador 1: ");
        jugador1 = reader.nextLine();
        System.out.println("Nombre del Jugador 2: ");
        jugador2 = reader.nextLine();
        
        //INICIAR PARTIDA
        Partida partida1 = new Partida();
        partida1.Partida();
        ganador = partida1.resultados();
        
        //NOMBRAR GANADORES
        if (ganador > 0)
        {
			System.out.println(jugador1 + " ha ganado la partida");
		}
		else if (ganador < 0)
		{
			System.out.println(jugador2 + " ha ganado la partida");
		}
        
    }
}

