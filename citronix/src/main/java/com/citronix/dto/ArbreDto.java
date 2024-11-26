package com.citronix.dto;

import com.citronix.entity.Arbre;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArbreDto {
    private int id;
    @NotNull(message = "La date de plantation est requise")
    @PastOrPresent
    private LocalDate datePlantation;
    @PositiveOrZero(message = "La productivité ne peut pas être négative")
    private int age;
    @PositiveOrZero(message = "La productivité ne peut pas être négative")
    private double productivite;
    @NotNull(message = "Le champ associé est requis")
    private int champId;
    private List<RecolteDto> recoltes;


}
