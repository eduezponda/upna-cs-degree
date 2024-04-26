package serialization;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static serialization.City.parseCities;
import static serialization.Country.parseCSV;

public class main {
    
    
    public static void main (String [] args) throws IOException, ClassNotFoundException{
        List<Country> countries = new ArrayList<>();
        List<Country> countriesPopAvg = new ArrayList<>();
        countries = parseCSV ("countries.csv");
        System.out.println("-----------EXERCISE 1-----------");
        System.out.println("The number of countries is: " + countries.size()); 
        
        System.out.println("-----------EXERCISE 2-----------");
        final double populationAverage = countries.stream().mapToDouble
        (Country::getPopulation).average().orElse(0);       
        System.out.println("Average is: " + populationAverage);
        System.out.println("Countries with more population than the average:");
        for (Country c: countries){
            if (c.getPopulation() > populationAverage){
                countriesPopAvg.add(c);
                System.out.println(c.getName());
            }
        }
        
        System.out.println("-----------EXERCISE 3-----------");
        Map <String,Integer> countriesPerRegion = countries.stream().collect
        (Collectors.groupingBy(Country::getRegion, Collectors.summingInt(c -> 1)));
        System.out.println("Number of countries in each region: ");
        countriesPerRegion.forEach((r, number) -> 
                System.out.println("Region: " + r + ": " + number));
        
        System.out.println("-----------EXERCISE 4-----------");
        List<City> cities = parseCities("cities.csv", countries);
        System.out.println("The number of cities is: " + cities.size());
        System.out.println("The number of cities with country is: " 
                + cities.stream().filter(c-> c.getCountry() != null).count());
        
        System.out.println("-----------EXERCISE 5-----------");
        List <City> subCities = new ArrayList<>();
        Comparator<City> comparator = Comparator.comparing(City::getPopulation).reversed();
        Collections.sort(cities, comparator);
        for (int i = 0; i <= 9 ; i++) {
            subCities.add(cities.get(i));
        }
        System.out.println("The countries of the 10 most populated cities are (decreasing order): ");
        for (City c: subCities){
            System.out.println("Country: " + c.getCountry().getName() + "     City: " 
                    + c.getName() + "     Population: " + c.getPopulation() );
        }
        
        System.out.println("-----------EXERCISE 6-----------");
        int maxPopulated, minPopulated, largestDif;
        String isFirstCity, nameCountryLargDif;
        
        largestDif = minPopulated = maxPopulated = 0;
        nameCountryLargDif = "";
        for (Country c: countries){
            maxPopulated = 0;
            isFirstCity = "True";
            for (City city : c.getCities()){
                if (city.getPopulation() > maxPopulated){
                    maxPopulated = city.getPopulation();
                    if (isFirstCity.equals("True") == true){
                        minPopulated = maxPopulated;
                        isFirstCity = "False";
                    }
                }
                else if (city.getPopulation() < minPopulated){
                    minPopulated = city.getPopulation();
                }
            }
            if ((maxPopulated - minPopulated) > largestDif){
                largestDif = maxPopulated - minPopulated;
                nameCountryLargDif = c.getName();
            }  
        }
        System.out.println("The country with the largest difference of inhabitants between"
                + "its most populated and least populated cities is: " + nameCountryLargDif);
        System.out.println("Difference: " + largestDif);
        
        System.out.println("-----------EXERCISE 7-----------");
        String fileName = "countriesMorePopAvg.csv";
        Country.writeCSV(fileName, countriesPopAvg);
        System.out.println("The file countriesMorePopAvg.csv with the data of the"
                + " countries with more population than the average has been created"
                + " correctly");
        
        System.out.println("-----------EXERCISE 8-----------");
        String fileName2 = "countries.bin";
        try{
            ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(fileName2));
            output.writeObject(countries);
            output.close();
        }
        catch (IOException e){
 
        }
        System.out.println("File countries.bin has been created correctly");
        
        System.out.println("-----------EXERCISE 9-----------");
        XStream xstream = new XStream ( new DomDriver() );
        String xml = xstream.toXML (countries);
        try {
                Files.write (Paths.get ("countries.xml"), xml.getBytes ());
        } catch (IOException e) {
                e.printStackTrace ();
        }
        System.out.println("File countries.xml has been created correctly");
        System.out.println("(There are some warnings because of the version of "
                + "the library) ");
        
        System.out.println("-----------EXERCISE 10-----------");
        System.out.println("CSV (Comma-separated values) is a simple text-based format consisting "
                + " rows and columns, making it easy to understand and use. It is easy to import/export:");
        System.out.println(" One of the disadvantages of CSV could be "
                + "limited data types: CSV only supports basic data types like strings and numbers, "
                + "which can limit its use for more complex data.");
        System.out.println("writeObject can serialize and deserialize complex data types "
                + "like objects and arrays, making it ideal for storing hierarchical data.");
        System.out.println("One disadvantage is that writeObject files can only be read and written by "
                + "applications that support the Java programming language, which can limit its use "
                + "for cross-platform data exchange.");
        System.out.println("Advantages of XML:");
        System.out.println("XML is a text-based format that is easy to read and"
                + " edit with a simple text editor");
        System.out.println("XML is a structured data format that supports hierarchical"
                + " relationships, making it ideal for storing complex data.");
        System.out.println("Disadvantages os XML:");
        System.out.println("XML files can be large and complex");
        
        System.out.println("-----------EXERCISE 11-----------");
        System.out.println("This process is called REFLECTION");
        System.out.println("When you call writeObject or use XStream to serialize an object,"
                + " reflection is used to examine the object's fields and methods "
                + "to obtain their values. So, it is not necessary to know the object in advance");
    }
}
