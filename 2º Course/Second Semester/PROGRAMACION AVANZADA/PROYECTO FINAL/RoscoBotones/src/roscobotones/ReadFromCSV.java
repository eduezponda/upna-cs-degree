package roscobotones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class ReadFromCSV {
    
    private String text;
    private final String reader;
    
    public ReadFromCSV(String reader) throws IOException
    {
        this.reader = reader;
        readCSVtoString();
    }
    public void readCSVtoString() throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(reader))) {
            text = "";
            String linea;
            boolean primerRenglón = true;
            while ((linea = br.readLine()) != null) 
            {
                if (primerRenglón) 
                {
                    primerRenglón = false;
                } 
                else 
                {
                    text += "\n";
                }
                text += linea;
            }
        }
    }

    public String getText() {
        return text;
    }

   
}
