/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileReader;

public class ej1 {
    
    public static void main(){
        
        File file = new File ("C:\\Users\\Eduardo\\Desktop");
        if (file.exists())
        {
            System.out.println(file.getName() + "exists");
            System.out.println(file.getAbsolutePath() + "path");
            System.out.println(file.getName() + "exists");
        }
        
    }
        
        
    
}
