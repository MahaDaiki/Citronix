package com.citronix.dto;

import com.citronix.entity.Saison;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecolteDto {
    private int id;
    @PastOrPresent
    @NotNull(message = "La date de récolte est requise")
    private LocalDate dateRecolte;
    @NotNull(message = "La saison est requise")
    private Saison saison;
    @PositiveOrZero(message = "La quantité totale ne peut pas être négative")
    private double quantiteTotal;
    private List<Integer> arbresIds;
    private List<VenteDto> venteIds;

}
