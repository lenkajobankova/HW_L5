package com.engeto.roomplants;

import java.util.Comparator;

public class PlantWateringComparator implements Comparator<Plant> {

    @Override
    public int compare(Plant plant1, Plant plant2) {
        return plant1.getWatering().compareTo(plant2.getWatering());
    }
}
