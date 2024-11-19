package com.citronix.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ferme {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Le nom est requis")
    private String nom;

    @NotBlank(message = "La localisation est requise")
    private String localisation;

    private double superficie;

    private LocalDate dateCreation;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL)
    @NotEmpty(message = "La liste des champs ne peut pas être vide")
    private List<Champ> champs;



}
