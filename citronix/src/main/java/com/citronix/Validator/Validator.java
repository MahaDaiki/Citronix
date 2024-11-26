package com.citronix.Validator;

import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import com.citronix.entity.Recolte;
import com.citronix.entity.Vente;

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
        double density = (double) numberOfTrees / (champ.getSuperficie()/10000);
        if (density > 100) {
            throw new IllegalArgumentException("La densité d'arbres par champ dépasse le maximum de 100 arbres par hectare.");
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

    public static void validateDatePlantation(Ferme ferme, LocalDate datePlantation) {
        if (datePlantation.isBefore(ferme.getDateCreation())) {
            throw new IllegalArgumentException(
                    "La date de plantation doit être après la date de création de la ferme (" + ferme.getDateCreation() + ")."
            );
        }
        if (datePlantation.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La date de plantation ne peut pas être dans le futur."
            );
        }
    }

    public static void validatePlantingBeforeHarvest(LocalDate datePlantation, LocalDate dateRecolte) {
        if (datePlantation.isAfter(dateRecolte)) {
            throw new IllegalArgumentException(
                    "La date de plantation (" + datePlantation + ") doit être avant la date de récolte (" + dateRecolte + ")."
            );
        }
    }

    public static void validateHarvestDate(LocalDate dateRecolte) {
        if (dateRecolte.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de récolte ne peut pas être dans le futur (" + dateRecolte + ").");
        }
    }
    public static void validateRecolteTotalQuantity(Recolte recolte, List<Vente> ventes) {
        double totalVentesQuantite = ventes.stream().mapToDouble(Vente::getQuantite).sum();

        if (totalVentesQuantite > recolte.getQuantiteTotal()) {
            throw new RuntimeException("La quantité totale des ventes dépasse la quantité totale de la récolte.");
        }
    }
    public static void validatePlantingPeriod(LocalDate datePlantation) {
        int month = datePlantation.getMonthValue();
        if (month < 3 || month > 5) {
            throw new IllegalArgumentException(
                    "La plantation des arbres est uniquement autorisée entre les mois de mars et mai. Date donnée : " + datePlantation
            );
        }
    }


}

