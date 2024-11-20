package com.citronix.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FermeSearchCriteria {
    private int id;
    private String nom;
    private String localisation;
    private double superficie;
    private LocalDate dateCreation;
}
