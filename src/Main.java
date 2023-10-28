import com.engeto.roomplants.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ListOfPlants listOfPlants = new ListOfPlants();
        try {
            listOfPlants = ListOfPlants.loadFromFile(Settings.loadFilename());
        } catch (PlantException e) {
            System.err.println("Chyba při čtení ze souboru: "+e.getLocalizedMessage());
        }

        System.out.println("Výpis rostlin s informacemi o příští zálivce:");
        for (Plant plant : listOfPlants.getListOfPlants()){
            System.out.println(plant.getWateringInfo());
        }

        listOfPlants.addPlant(new Plant("Citrus", LocalDate.of(2022,5,8), 5));
        listOfPlants.addPlant(new Plant("Oxalis", LocalDate.of(2021,3,17), 3));
        listOfPlants.removePlant(3);
        System.out.println("Seznam rostlin po úpravách:\n"+listOfPlants);

        try {
            ListOfPlants.saveToFile(Settings.writeFilename(), listOfPlants);
        } catch (PlantException e) {
            System.err.println("Chyba při ukádání do souboru: "+e.getLocalizedMessage());
        }

        try {
            listOfPlants = ListOfPlants.loadFromFile(Settings.writeFilename());
            System.out.println("Opětovné vypsání po načtení uloženého souboru:\n"+listOfPlants.getListOfPlants());
        } catch (PlantException e) {
            System.err.println("Chyba při čtení ze souboru: "+e.getLocalizedMessage());
        }

        List<Plant> loadedListOfPlants = new ArrayList<>(listOfPlants.getListOfPlants());
        Collections.sort(loadedListOfPlants);
        System.out.println("Soubor seřazený dle názvu rostlin:\n"+loadedListOfPlants);
        Collections.sort(loadedListOfPlants, new PlantWateringComparator().reversed());
        System.out.println("Seznam rostlin seřazený dle zálivky:\n"+loadedListOfPlants);
    }
}