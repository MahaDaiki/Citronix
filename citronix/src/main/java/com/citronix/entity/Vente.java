package com.citronix.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vente {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "La date de vente est requise")
    @PastOrPresent
    private LocalDate date;

    @PositiveOrZero(message = "Le prix unitaire ne peut pas être négatif")
    private double prixUnitaire;

    @PositiveOrZero(message = "La quantité ne peut pas être négative")
    private double quantite;

    @PositiveOrZero(message = "Le revenu ne peut pas être négatif")
    private double revenu;

    @ManyToOne
    @NotNull(message = "La récolte associée est requise")
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;
}
