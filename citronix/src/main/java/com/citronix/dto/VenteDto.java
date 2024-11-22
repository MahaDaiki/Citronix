package com.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenteDto {

    private int id;
    private LocalDate date;
    private double prixUnitaire;
    private double quantite;
    private double revenu;
    private int recolteId;
}
