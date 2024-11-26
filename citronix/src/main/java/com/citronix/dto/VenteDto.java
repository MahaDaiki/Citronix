package com.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenteDto {

    private int id;
    @NotNull(message = "La date de vente est requise")
    @PastOrPresent
    private LocalDate date;
    @PositiveOrZero(message = "Le prix unitaire ne peut pas être négatif")
    private double prixUnitaire;
    @PositiveOrZero(message = "La quantité ne peut pas être négative")
    private double quantite;
    @PositiveOrZero(message = "Le revenu ne peut pas être négatif")
    private double revenu;
    @NotNull(message = "La récolte associée est requise")
    private int recolteId;
}
