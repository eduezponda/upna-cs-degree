/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package casino;

/**
 *
 * @author gazco
 */
public class CasinoExtendido {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Jugador jugadorA = new Jugador(new DadoTrucado(1), new Dado());
        Jugador jugadorB = new Jugador(new DadoTrucado(6), new Dado());
        Turno[] turnos = new Turno[5];
        for(int i=0; i < turnos.length; i++)
        {
            turnos[i] = new Turno();
        }
        
        for (int i = 0; i <= 100; i++)
        {
           Partida partida = new Partida(turnos, jugadorA, jugadorB); 
           partida.solicitarNombres();
           partida.jugarTurnos();
           partida.mostrarGanador();
        }
        
        
        
        
        
        
        }
    }
    

