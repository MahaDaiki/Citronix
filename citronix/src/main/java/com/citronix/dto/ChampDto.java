package com.citronix.dto;


import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChampDto {

    private int id;

    @NotBlank(message = "Le nom du champ est requis")
    private String nom;

    @NotNull(message = "La superficie est requise")
    @Min(value = 1000, message = "La superficie du champ doit être au moins 1000 m² (0.1 hectare).")
    private double superficie;

    @NotNull(message = "La ferme associée est requise")
    private int fermeId;

    private List<ArbreDto> arbres;


}
