public class Partida{

    private Turno[] turnos1,turnos2;
    
    public void partida(){
        Turno[] turnos1 = new Turno[5];
        Turno[] turnos2 = new Turno[5];
        turnos1[0] = new Turno();
        turnos1[0].turno();
        turnos2[0] = new Turno();
        turnos2[0].turno();
        turnos1[1] = new Turno();
        turnos1[1].turno();
        turnos2[1] = new Turno();
        turnos2[1].turno();
        turnos1[2] = new Turno();
        turnos1[2].turno();
        turnos2[2] = new Turno();
        turnos2[2].turno();
        turnos1[3] = new Turno();
        turnos1[3].turno();
        turnos2[3] = new Turno();
        turnos2[3].turno();
        turnos1[4] = new Turno();
        turnos1[4].turno();
        turnos2[4] = new Turno();
        turnos2[4].turno();
    }

}
