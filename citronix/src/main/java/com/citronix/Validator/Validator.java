package com.citronix.Validator;

import com.citronix.entity.Champ;

import java.time.LocalDate;
import java.util.List;

public class Validator {
    public static void validateSuperficie(double superficie, List<Champ> champs) {
        double totalChampsSuperficie = champs.stream().mapToDouble(Champ::getSuperficie).sum();

        //all champs superficies is less than ferme superficie
        if (totalChampsSuperficie >= superficie) {
            throw new IllegalArgumentException("The total area of the fields must be less than the ferme's area.");
        }

        //no champ exceeds 50% of the ferme superficie
        for (Champ champ : champs) {
            if (champ.getSuperficie() > (superficie / 2)) {
                throw new IllegalArgumentException("No field can exceed 50% of the ferme's area.");
            }
        }

        // the number of fields does not more than 10
        if (champs.size() > 10) {
            throw new IllegalArgumentException("A ferme cannot have more than 10 fields.");
        }
    }

    //it cannot be in the future
    public static void validateDateCreation(LocalDate dateCreation) {
        if (dateCreation.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("The creation date cannot be in the future.");
        }
    }


    public static void validateChampSuperficie(double superficie) {
        if (superficie > 1000) {
            throw new IllegalArgumentException("The field area must be at least 0.1 hectare.");
        }
    }


    public static void validateTreeDensity(Champ champ, int numberOfTrees) {
        double density = numberOfTrees / champ.getSuperficie();
        if (density > 100) {
            throw new IllegalArgumentException("The density of trees per field exceeds the maximum of 100 trees per hectare.");
        }
    }

    public static void validateFermeSuperficie(double superficie) {
        if (superficie < 1000) {
            throw new IllegalArgumentException("La superficie doit être supérieure ou égale à 1000 m² (0.1 hectare).");
        }

        if (superficie > 10000000) {
            throw new IllegalArgumentException("La superficie ne peut pas dépasser 10 000 000 m² (100 hectares).");
        }
    }
}

