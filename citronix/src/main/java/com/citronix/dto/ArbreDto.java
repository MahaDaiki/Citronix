package com.citronix.dto;

import com.citronix.entity.Arbre;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ArbreDto {
    private int id;
    private LocalDate datePlantation;
    private int age;
    private double productivite;
    private int champId;

    public static ArbreDto toDto(Arbre arbre) {
        ArbreDto dto = new ArbreDto();
        dto.setId(arbre.getId());
        dto.setDatePlantation(arbre.getDatePlantation());
        dto.setAge(arbre.getAge());
        dto.setProductivite(arbre.getProductivite());
        dto.setChampId(arbre.getChamp().getId());
        return dto;
    }

    public static Arbre toEntity(ArbreDto dto) {
        Arbre arbre = new Arbre();
        arbre.setId(dto.getId());
        arbre.setDatePlantation(dto.getDatePlantation());
        return arbre;
    }
}
