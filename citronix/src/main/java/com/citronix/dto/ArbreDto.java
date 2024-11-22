package com.citronix.dto;

import com.citronix.entity.Arbre;
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
    private LocalDate datePlantation;
    private int age;
    private double productivite;
    private int champId;
    private List<RecolteDto> recoltes;


}
