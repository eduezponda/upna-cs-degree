/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casino;

/**
 *
 * @author gazco
 */
public class Jugador {
    
    public int puntuacion;
    public boolean esOjosDeTigre;
    public String nombre;
    public int numeroTurnosGanados;
    public Dado dado1;
    public Dado dado2;
    
    public Jugador(Dado dado1, Dado dado2)
    {
        this.dado1 = dado1;
        this.dado2 = dado2;
        this.nombre = "";
        this.numeroTurnosGanados = 0;
        this.puntuacion = 0;
        this.esOjosDeTigre = false;
    }
    
    public void juega()
    {
        
        dado1.tirar();
        int resultadoDado1 = dado1.valor();
        dado2.tirar();
        int resultadoDado2 = dado2.valor();
        this.puntuacion = resultadoDado1 + resultadoDado2;
        this.esOjosDeTigre = resultadoDado1 == 1 && resultadoDado2 == 1;
    }
    
    public void computarVictoria()
    {
        numeroTurnosGanados++; //Mal redactado en la copia
    }
}
