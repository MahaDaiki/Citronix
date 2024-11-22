package com.citronix.dto;

import com.citronix.entity.Arbre;
import lombok.Data;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Data
public class ArbreDto {
    private int id;
    private LocalDate datePlantation;
    private int age;
    private double productivite;
    private int champId;
//    private List<RecolteDto> recoltes;


}
