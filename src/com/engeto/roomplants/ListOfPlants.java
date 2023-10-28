package com.engeto.roomplants;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListOfPlants {
    List<Plant> listOfPlants = new ArrayList<>();

    public ListOfPlants(List<Plant> listOfPlants) {
        this.listOfPlants = listOfPlants;
    }
    public ListOfPlants(){}
    public static ListOfPlants loadFromFile(String filename) throws PlantException {
        ListOfPlants result = new ListOfPlants();
        int lineNumber = 1;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                parseLine(line, result, lineNumber);
                lineNumber++;
            }

        } catch (FileNotFoundException e) {
            throw new PlantException("Nenalezen soubor "+filename+": "+e.getLocalizedMessage());
        }

        return result;
    }

    private static void parseLine(String line, ListOfPlants result, int lineNumber) throws PlantException {
        String[] blocks = line.split(Settings.filePlantDelimiter());
        int numOfBlocks = 5;
        if (numOfBlocks != 5){
            throw new PlantException("Chybně zadaný počet bloků na řádku č."+
                    lineNumber+"("+line+"). Zadaný počet bloků: "+numOfBlocks);
        }
        Plant newPlant = new Plant(blocks);
        result.addPlant(newPlant);
        rightWrittenDate(blocks, line, lineNumber);
        rightWrittenFreq(blocks, line, lineNumber);
    }

    public static void saveToFile(String filename, ListOfPlants list) throws PlantException {
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Plant plant : list.listOfPlants){
                writer.println(plant.getName()+Settings.filePlantDelimiter()+
                        plant.getNotes()+Settings.filePlantDelimiter()+
                        plant.getPlanted()+Settings.filePlantDelimiter()+
                        plant.getWatering()+Settings.filePlantDelimiter()+
                        plant.getFrequencyOfWatering());
            }
        } catch (IOException e) {
            throw new PlantException("Chyba při zápisu do souboru: "+filename+
                    ": "+e.getLocalizedMessage());
        }
    }
    //region Getters and Setters
    public List<Plant> getListOfPlants(){
        return new ArrayList<>(listOfPlants);
    }
    public void addPlant(Plant newPlant){
        listOfPlants.add(newPlant);
    }
    public void removePlant(int index){
            if (index>listOfPlants.size()){
                System.err.println("Tolik rostlin v seznamu nemáme! Zadán index: "+index);
            }else{
                listOfPlants.remove(index);
            }
    }
    public void getPlant(int index){
        listOfPlants.get(index);
    }
    private static void rightWrittenDate(String[] blocks, String line, int lineNumber) throws PlantException {
        LocalDate watering = LocalDate.parse(blocks[3].trim());
        LocalDate planted = LocalDate.parse(blocks[2].trim());
        if (watering.isBefore(planted)){
            throw new PlantException("Špatně zadané datum poslední zálivky("+
                    watering+") nebo datum zasazení rostliny("+planted+
                    ") na řádku č."+lineNumber+" ("+line+").");
        }
    }
    private static void rightWrittenFreq(String[] blocks, String line, int lineNumber) throws PlantException {
        int freqOfWatering = Integer.parseInt(blocks[4]);
        if (freqOfWatering<1){
            throw new PlantException("Zadána nízká frekvence zálivky("+
                    freqOfWatering+") na řádku č."+lineNumber+" ("+line+").");
        }
    }

    //endregion

    @Override
    public String toString() {
        return ""+listOfPlants;
    }
}
