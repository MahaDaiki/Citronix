package com.citronix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailRecolteDto {
    private int id;
    @PositiveOrZero(message = "La quantité ne peut pas être négative")
    private double quantite;
    @NotNull(message = "La récolte associée est requise")
    private int recolteId;
    @NotNull(message = "L'arbre associé est requis")
    private int arbreId;
}
