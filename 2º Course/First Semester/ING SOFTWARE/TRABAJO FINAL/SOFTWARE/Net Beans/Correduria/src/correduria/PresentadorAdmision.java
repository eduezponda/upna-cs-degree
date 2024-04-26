package correduria;   //

public class PresentadorAdmision{

    private String aseguradora;
    private VistaAdmision vista;
    private Cliente cliente;
    private Bien bien;
    private Aseguradora aseguradoras[];
    private Oferta ofertas[];
    private final int N = 4;
    
    public PresentadorAdmision(VistaAdmision vista) {
        this.vista = vista;
        aseguradoras= new Aseguradora[N];
        ofertas = new Oferta[N];
        aseguradoras[0]=new Mafro();
        aseguradoras[1]=new LineaIndirecta();
        aseguradoras[2]=new Adasles();
        aseguradoras[3]=new CatalanaOriente();
        this.cliente = new Cliente();
        this.bien = new Bien();
    }
    public void admitirCliente(){
        cliente.annoNacimiento = vista.consultarAnnoNacimiento();
        cliente.salarioAnual = vista.consultarSalarioAnual();
    }
    public void admitirBien(){
        bien.tipoDeBien = vista.consultarTipoDeBien();
        bien.valorDeBien = vista.consultarValorDeBien();
        while(!bien.bienCorrecto(bien.tipoDeBien,bien.valorDeBien)){
            vista.printBienIncorrecto();
            bien.tipoDeBien = vista.consultarTipoDeBien();
            bien.valorDeBien = vista.consultarValorDeBien();
        }
    }
    public Oferta calcularOferta(){
        Oferta mejorOferta;
        for(int i = 0;i<N;i = i + 1){
            ofertas[i] = new Oferta(aseguradoras[i]);
            ofertas[i].calcularOferta(bien,cliente);
        }
        mejorOferta = ofertas[0];
        for(int i = 1;i<N;i = i + 1){
            if(ofertas[i].importe<mejorOferta.importe){
                mejorOferta = ofertas[i];
            }
            if((ofertas[i].importe==mejorOferta.importe) && (ofertas[i].comision>mejorOferta.comision)){
                mejorOferta = ofertas[i];
            }
        }
        return mejorOferta;
    }
    public void imprimirMejorOferta(Oferta oferta){
        System.out.println(oferta.aseguradora.nombre()+" | "+oferta.importe+" | "+oferta.comision);
    } 
    public String lineaMejorOferta(Oferta oferta){
        return(oferta.aseguradora.nombre()+" | "+oferta.importe+" | "+oferta.comision);
    }   
}