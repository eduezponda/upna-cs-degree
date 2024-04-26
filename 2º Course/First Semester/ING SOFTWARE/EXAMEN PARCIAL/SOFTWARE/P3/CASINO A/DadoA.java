public class Dado{
    
    private int dado;
    
    public void tirada(){
        dado = (int)(Math.random()*6) + 1;
    }
    
    public int resultado(){
        return dado;
    }
    
}
