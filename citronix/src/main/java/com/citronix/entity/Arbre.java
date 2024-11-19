package com.citronix.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Arbre {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "La date de plantation est requise")
    private LocalDate datePlantation;

    @PositiveOrZero(message = "L'âge de l'arbre ne peut pas être négatif")
    private int age;

    @PositiveOrZero(message = "La productivité ne peut pas être négative")
    private double productivite;

    @NotNull(message = "Le champ associé est requis")
    @ManyToOne
    private Champ champ;

    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL)
    private List<DetailRecolte> detailRecoltes;
}
