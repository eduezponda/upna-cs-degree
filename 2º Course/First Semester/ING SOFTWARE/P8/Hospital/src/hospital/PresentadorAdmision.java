/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

/**
 *
 * @author alumno
 */
public class PresentadorAdmision {
    private IVistaAdmision vista;
    private Paciente[] pacientes;
    private final Integer NUMERO_MAXIMO_PACIENTES = 10;
    public PresentadorAdmision(IVistaAdmision vista)
    {
        this.vista = vista;
        pacientes = new Paciente[NUMERO_MAXIMO_PACIENTES];
    }
    public void admitirPacientes()
    {
        int i=0;
        while(i<NUMERO_MAXIMO_PACIENTES )
        {
            String nombre = this.vista.consultarNombre();
            String apellido = this.vista.consultarApellido();
            Integer anno = this.vista.consultarAnnoNacimiento();
            String[] sintomas = this.vista.consultarSintomas();
            Paciente paciente = new Paciente(nombre, apellido, anno, sintomas);
            this.pacientes[i] = paciente;
            i++;
            if(!this.vista.continuaRegistro()) break;
        }
    }
    public void mostrarPacientesAislamiento()
    {
        for(Paciente paciente : pacientes)
        {
            if(paciente==null) continue;
            if(paciente.requiereAislamiento())
            {
                this.vista.mostrarEnListado(paciente.apellidosComaNombre());
            }
        }
    }
}