//Guillermo Azcona Recari
//PRÁCTICA 4 Casino
//13/10/2021

import java.lang.*;
import java.io.*;
import java.util.Scanner;

class Partida //Array de turnos
{		
	private int cuentaGanadores;
	private int[] tablaJugador1 = new int[6]; //Variable tabla con 5 sumas de cada turno del jugador 1
	private int[] tablaJugador2 = new int[6]; //Variable tabla con 5 sumas de cada turno del jugador 2
	
    public void Partida()
    {	
        cuentaGanadores = 0;
        System.out.println("Comienza la partida");
        for(int i = 0; i <= 5; i++) //Bucle de 5 turnos cada uno por partida
        {
            System.out.println("    Turno " + (i+1));
            System.out.println("        Juega el jugador 1" );
            Turno turnoJugador1 = new Turno(); //Crear turno (2 tiradas del jugador 1)
            turnoJugador1.Turno();
            tablaJugador1[i] = turnoJugador1.sumaDados();
            
            System.out.println("        Juega el jugador 2" );
            Turno turnoJugador2 = new Turno(); //Crear turno (2 tiradas del jugador 2)
            turnoJugador2.Turno();
            tablaJugador2[i] = turnoJugador2.sumaDados();
            
            //SUMAR AL CONTADOR GANADORES
            if (tablaJugador1[i] > tablaJugador2[i])
            {
				cuentaGanadores += 1;
			}
			else if (tablaJugador1[i] < tablaJugador2[i])
			{
				cuentaGanadores -= 1;
			}
			else
			{
				continue;
			}
			
			//COMPROBAR SI ES OJOS DE TIGRE
			if ((tablaJugador1[i] == 2) && (tablaJugador2[i] != 2)) //Ojos de tigre del jugador 1.
			{
				cuentaGanadores = cuentaGanadores + 6;
				break;
			}
			else if ((tablaJugador2[i] == 2) && (tablaJugador1[i] != 2)) //Ojos de tigre del jugador 2.
			{
				cuentaGanadores = cuentaGanadores - 6;
				break;
			}
        }  
    }
    

    public int resultados() //Devuelve los Arrays con la suma de los dados por turno y por jugador
    {
			return cuentaGanadores;
	}


}

    
