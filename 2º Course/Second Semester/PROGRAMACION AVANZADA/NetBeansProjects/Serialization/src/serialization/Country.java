package serialization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable{
    private String name;
    private String region;
    private int population;
    private int area;
    private float gdp;
    private List<City> cities = new ArrayList<>();
    
    public Country(String name, String region, int population, int area, float gdp) {
        this.name = name;
        this.region = region;
        this.population = population;
        this.area = area;
        this.gdp = gdp; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public List<City> getCities() {
        return cities;
    }

    public void setCity(List<City> cities) {
        this.cities = cities;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public float getGdp() {
        return gdp;
    }

    public void setGdp(float gdp) {
        this.gdp = gdp;
    }
    
    
    
    public static List<Country> parseCSV(String file){
        List<Country> countries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                try{
                
                    line = line.replace(", ","").replace("\"","");
                    String[] parts = line.split(",");

                    if ((parts[0].equals("") == false) && (parts[1].equals("") == false)
                            && (parts[2].equals("") == false) && (parts[3].equals("") == false) 
                            && (parts[8].equals("") == false)){
                        String name = parts[0].trim(); 
                        String region = parts[1].trim();
                        int population = Integer.parseInt(parts[2].trim());
                        int area = Integer.parseInt(parts[3].trim());
                        float gdp = Float.parseFloat(parts[8].trim());
                        Country country = new Country(name, region, population, area, gdp);
                        countries.add(country);
                    }
                }
                catch (NumberFormatException ne){
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countries;
    }
    
    public static void writeCSV(String fileName, List<Country> countries){
        try ( FileWriter f = new FileWriter(fileName); ) {
            
            for (Country c : countries) {
                f.write(c.getName() + "," + c.getRegion() +  "," + c.getPopulation()
                        + "," + c.getArea() + "," + c.getGdp());
                for (City city: c.getCities()){
                    f.write("," + city.getName());
                }
                f.write("\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}          
            
            
    

    

