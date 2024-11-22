package com.citronix.dto;


import com.citronix.entity.Saison;
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
    private LocalDate dateRecolte;
    private Saison saison;
    private double quantiteTotal;
    private List<Integer> arbresIds;
    private List<Integer> venteIds;
}
