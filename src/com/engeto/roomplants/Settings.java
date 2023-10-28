package com.engeto.roomplants;

public class Settings {
    public static final String LOAD_FILENAME = "kvetiny.txt";
    public static final String WRITE_FILENAME = "kvetiny_nove.txt";
    public static final String FILE_PLANT_DELIMITER = "\t";

    public static String loadFilename(){
        return LOAD_FILENAME;
    }
    public static String writeFilename(){
        return WRITE_FILENAME;
    }
    public static String filePlantDelimiter(){
        return FILE_PLANT_DELIMITER;
    }
}
