import java.lang.*;
import java.io*;
import java.util.Scanner;
import Turno;
import Partida;

package Casino;

public class Casino //Main
{
    public static void main(String[] args)
    {
		private String jugador1, jugador2;
		private int ganador;
        //PEDIR NOMBRES
        Scanner reader = new Scanner(System.in);
        char numero;
        System.out.println("Nombre del Jugador 1: ");
        String jugador1 = scan.nextLine();
        System.out.println("Nombre del Jugador 2: ");
        String jugador2 = scan.nextLine();
        
        //INICIAR PARTIDA
        Partida partida1 = new Partida();
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

