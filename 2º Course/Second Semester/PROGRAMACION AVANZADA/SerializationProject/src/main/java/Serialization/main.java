/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static serialization.City.parseCities;
import static serialization.Country.parseCSV;

/**
 *
 * @author Eduardo
 */
public class main {
    
    
    public static void main (String [] args) throws IOException{
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
            System.out.println("Country: " + c.getCountry().getName() + " City: " + c.getName() + " Population: "
                    + c.getPopulation() );
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
        String fileName = "countriesMorePopAvg.bin";
        Country.writeCSV(fileName, countriesPopAvg);
        
    }
}
