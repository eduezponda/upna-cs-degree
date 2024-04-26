package finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ikergo55
 */
public class Utils {
    public static double uniform(double min, double max){
        return Math.random() * (max-min) + min;
    }
    
    public static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int[][] readFile(String file){
    int[][] l = new int[1000][1000];
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));

            String line;
            int i = 0;

            while ((line = reader.readLine() )!= null){
                String[] parts = line.split(",");

                l[i][0] = Integer.parseInt(parts[2].trim());
                l[i][1] = Integer.parseInt(parts[3].trim());

                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

    return l;
    }
}
