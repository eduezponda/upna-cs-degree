package correduria;
import org.junit.Test;
import static org.junit.Assert.*;

public class CorreduriaTest {
    
    @Test
    public void prueba1(){
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaFalsa(2005,15000,"vehiculo",30000));
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.imprimirMejorOferta(presentador.calcularOferta());
        assertEquals("LINEA INDIRECTA | 900 | 9",presentador.lineaMejorOferta(presentador.calcularOferta()));
    }
    @Test
    public void prueba2(){
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaFalsa(2021,52000,"vivienda",150000));
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.imprimirMejorOferta(presentador.calcularOferta());
        assertEquals("ADASLES | 3000 | 150",presentador.lineaMejorOferta(presentador.calcularOferta()));
    }
    @Test
    public void prueba3(){
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaFalsa(1999,500,"vivienda",50000));
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.imprimirMejorOferta(presentador.calcularOferta());
        assertEquals("ADASLES | 1000 | 50",presentador.lineaMejorOferta(presentador.calcularOferta()));
    }
    @Test
    public void prueba4(){
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaFalsa(2020,4,"vivienda",1000000));
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.imprimirMejorOferta(presentador.calcularOferta());
        assertEquals("MAFRO | 20000 | 600",presentador.lineaMejorOferta(presentador.calcularOferta()));
    }
    @Test
    public void prueba5(){
        PresentadorAdmision presentador = new PresentadorAdmision(new VistaFalsa(1981,15000,"vehiculo",30000));
        presentador.admitirCliente();
        presentador.admitirBien();
        presentador.imprimirMejorOferta(presentador.calcularOferta());
        assertEquals("ADASLES | 600 | 6",presentador.lineaMejorOferta(presentador.calcularOferta()));
    }
}
