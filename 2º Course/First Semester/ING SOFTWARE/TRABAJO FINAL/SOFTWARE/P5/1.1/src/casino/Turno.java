/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino;

/**
 *
 * @author gazco
 */
public class Turno 
{
    
    private Jugador ganador;
    public void jugar(Jugador jugador1, Jugador jugador2)
    {
        this.ganador = null;
        jugador1.juega();
        jugador2.juega();
        if(jugador1.esOjosDeTigre && !jugador2.esOjosDeTigre)
        {
            jugador1.computarVictoria();
            this.ganador = jugador1;
            return;
        }
        if(jugador2.esOjosDeTigre && !jugador1.esOjosDeTigre)
        {
            jugador2.computarVictoria();
            this.ganador = jugador2;
            return;
        }
        if(jugador1.puntuacion>jugador2.puntuacion)
        {
            jugador1.computarVictoria();
            this.ganador = jugador1;
        }
        if(jugador1.puntuacion<jugador2.puntuacion)
        {
            jugador2.computarVictoria();
            this.ganador = jugador2;
        }
    }
    
    public Jugador obtenerGanador()
    {
        return ganador;
    }
}


