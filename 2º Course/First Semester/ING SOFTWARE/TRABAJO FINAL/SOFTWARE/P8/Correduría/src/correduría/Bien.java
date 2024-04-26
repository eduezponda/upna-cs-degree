package correduría;

/**
 *
 * @author Sol Blasco Di Rosa (blasco.138141)
 */
public class Bien {
    public String tipoBien;
    
    public int valorBien;
    
    public Bien(){
        this.tipoBien = new String();
        
        this.valorBien = 0;
    }
    
    public Bien(String tipoBien, Integer valorBien){
        this.tipoBien = tipoBien;
        
        this.valorBien = valorBien;
    }
    
}
