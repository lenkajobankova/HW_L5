package com.engeto.roomplants;

import java.time.LocalDate;

public class Plant implements Comparable<Plant>{
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    //region Construstors

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.frequencyOfWatering = frequencyOfWatering;
    }
    public Plant(String name, LocalDate planted, int frequencyOfWatering){
        this(name, "",planted, LocalDate.now(), frequencyOfWatering);
    }
    public Plant(String name){
        this(name, LocalDate.now(), 7);
    }
    public Plant (String[] blocks) {
        this.name = blocks[0].trim();
        this.notes = blocks[1].trim();
        this.planted = LocalDate.parse(blocks[2].trim());
        this.watering = LocalDate.parse(blocks[3].trim());
        this.frequencyOfWatering = Integer.parseInt(blocks[4].trim());
    }

    //endregion

    //region Getters and Settters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.compareTo(planted)<0){
            throw new PlantException("Datum zálivky nemůže být dřívější než datum zasazení rostliny"+
                    ", zadáno: "+watering);
        }
        this.watering = watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if(frequencyOfWatering<=0){
            throw new PlantException("Frekvence zálivky nemůže být menší než 1" +
                    ", zadáno: "+frequencyOfWatering);
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public String getWateringInfo(){
        String result = "Jméno rostliny: "+getName()+", datum poslední zálivky: "+
                getWatering()+", datum doporučené příští zálivky: "+
                getWatering().plusDays(getFrequencyOfWatering());
        return result;
    }

    //endregion

    @Override
    public String toString() {
        return "Jméno rostliny: " + name +
                ", poznámky: " + notes +
                ", zasazena: " + planted +
                ", poslední zálivka: " + watering +
                ", četnost zálivky: 1x za " + frequencyOfWatering +
                " dní.";
    }

    @Override
    public int compareTo(Plant otherPlant) {
        return name.compareTo(otherPlant.getName());
    }
}
