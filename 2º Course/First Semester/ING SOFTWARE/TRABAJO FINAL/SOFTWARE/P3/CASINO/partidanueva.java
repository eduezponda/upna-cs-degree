import java.lang.*;
import java.io*;
import java.util.Scanner;
import turno;

public class Partida
{
		private int[] tabla = new int[5]; //Variable tabla con 5 números
		private int dif; //Diferencia
		private Turno turno; //Crear un turno, elemento importado
		
		public Partida()
		{
				dif = 0;
				this.turno = new Turno();
				this.esOjosDeTigre = false
		}
		
		public void EjecutarPartida()
		{
				System.out.println("Comienza la partida");
				for (int i = 0; i<5; i++)
				{
						System.out.println("	Turno " + (i+1));
						System.out.println("		Juega el jugador 1");
				}
		}
}
