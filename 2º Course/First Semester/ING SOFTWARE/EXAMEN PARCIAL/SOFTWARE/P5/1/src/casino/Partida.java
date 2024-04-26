
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino;

import java.util.Scanner;

/**
 *
 * @author gazco
 */
public class Partida
{
    public Jugador ganador;
    private Jugador jugador1;
    private Jugador jugador2;
    private Turno[] turnos;
    private Scanner scan;
    public Partida(Turno[] turnos, Jugador jugador1, Jugador jugador2) {
    this.scan = new Scanner(System.in);
    this.turnos = turnos;
    this.ganador = null;
    this.jugador1 = jugador1;
    this.jugador2 = jugador2;
    }
    private String obtenerNombreJugador()
    {
        System.out.print("Nombre Jugador: ");
        return scan.next();
    }

    private void mostrarResultado(Jugador jugador)
    {
        System.out.println(" Juega " + jugador.nombre);
        System.out.println(" Primer dado es " + String.valueOf(jugador.dado1.valor()));
        System.out.println(" Segundo dado es " + String.valueOf(jugador.dado2.valor()));
        if(jugador.esOjosDeTigre)
        {
            System.out.println(" Enhorabuena, Ojos de Tigre!");
            return;
        }
        System.out.println(" Total: " + String.valueOf(jugador.puntuacion));
    }

    public void solicitarNombres()
    {
        this.jugador1.nombre = this.obtenerNombreJugador();
        this.jugador2.nombre = this.obtenerNombreJugador();
    }

    public void jugarTurnos()
    {
        System.out.println("Comienza la partida");
        for(int i=0; i<5; i++)
        {
            System.out.println(" Turno " + String.valueOf(i+1));
            turnos[i].jugar(this.jugador1, this.jugador2);
            Jugador ganadorTurno = turnos[i].obtenerGanador();
            this.mostrarResultado(this.jugador1);
            this.mostrarResultado(this.jugador2);
            
            if(ganadorTurno != null) System.out.println(" Empate!");
            else
            {
                System.out.println(" Ha ganado el turno " + ganadorTurno.nombre);
                if(ganadorTurno.esOjosDeTigre)
                {
                    this.ganador = ganadorTurno;
                    return;
                }
            }
        }
        if(this.jugador1.numeroTurnosGanados> this.jugador2.numeroTurnosGanados)
        {
            this.ganador = this.jugador1;
        }
        if(this.jugador2.numeroTurnosGanados> this.jugador1.numeroTurnosGanados)
        {
            this.ganador = this.jugador2;
        }
    }
    
    public void mostrarGanador()
    {
        if(this.ganador == null)
        {
            System.out.println("Partida Empatada");
            return;
        }
        System.out.println("Ganador de la partida: " + this.ganador.nombre);
    }
}
    

