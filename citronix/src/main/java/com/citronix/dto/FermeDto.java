package com.citronix.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FermeDto {
    private int id;
    @NotBlank(message = "Le nom est requis")
    private String nom;
    @NotBlank(message = "La localisation est requise")
    private String localisation;
    @Min(value = 1000, message = "La superficie doit être supérieure ou égale à 1000 m² (0.1 hectare).")
    @Max(value = 10000000, message = "La superficie ne peut pas dépasser 10 000 000 m² (100 hectares).")
    @NotNull(message = "Superficie ne peut pas etre null")
    private double superficie;
    @NotNull
    @PastOrPresent
    private LocalDate dateCreation;
    @NotEmpty(message = "La liste des champs ne peut pas être vide")
    @Size(max = 10, message = "Une ferme ne peut contenir plus de 10 champs")
    private List<ChampDto> champs;


}
