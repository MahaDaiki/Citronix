package com.citronix.dto;

import com.citronix.entity.Ferme;
import com.citronix.entity.Champ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FermeDto {
    private int id;
    private String nom;
    private String localisation;
    private double superficie;
    private LocalDate dateCreation;
    private List<ChampDto> champs;


}
