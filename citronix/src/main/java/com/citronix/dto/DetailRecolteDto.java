package com.citronix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailRecolteDto {
    private int id;
    private double quantite;
    private int recolteId;
    private int arbreId;
}
