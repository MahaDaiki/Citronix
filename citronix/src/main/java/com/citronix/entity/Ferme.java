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
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ferme {
    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom est requis")
    private String nom;

    @Column(nullable = false)
    @NotBlank(message = "La localisation est requise")
    private String localisation;

    @Column(nullable = false)
    @Min(value = 1000, message = "La superficie doit être supérieure ou égale à 1000 m² (0.1 hectare).")
    @Max(value = 10000000, message = "La superficie ne peut pas dépasser 10 000 000 m² (100 hectares).")
    @NotNull(message = "Superficie ne peut pas etre null")
    private double superficie;

    @NotNull
    @Column(nullable = false)
    @PastOrPresent
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL)
    @NotEmpty(message = "La liste des champs ne peut pas être vide")
    @Size(max = 10, message = "Une ferme ne peut contenir plus de 10 champs")
    private List<Champ> champs;



}
