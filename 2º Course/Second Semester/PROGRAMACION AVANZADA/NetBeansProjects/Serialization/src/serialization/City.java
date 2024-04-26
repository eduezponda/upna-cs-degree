package serialization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class City implements Serializable{
    private String name;
    private int population;
    private Country country;

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }
    
    public static List<City> parseCities(String fileName, List<Country> countries){
        ArrayList<City> cities = new ArrayList<>();
        
        HashMap<String, Country> countriesByName = new HashMap<>();
		if (countries != null)
			for (Country c : countries) 
				countriesByName.put(c.getName().toLowerCase().trim(), c);
        
         try (BufferedReader br =
                new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                line = line.replace(", ", "").replace("\"", "");
                String[] parts = line.split(",");
                try{
                    City city = new City(
                            parts[0], 
                            Integer.parseInt(parts[9] ) ); 
                    
                    final String countryName = parts[4].toLowerCase().trim(); 
                    final Country country = countriesByName.get(countryName);
                    if (country != null){
                        city.setCountry(country);
                        country.getCities().add(city);
                    }
                   cities.add(city); 
                }catch(NumberFormatException ne){}
            }
        } catch (IOException ex) {
            Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    
}
