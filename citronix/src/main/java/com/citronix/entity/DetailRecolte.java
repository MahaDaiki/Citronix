package com.citronix.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DetailRecolte {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La saison est requise")
    private Saison saison;

    @PositiveOrZero(message = "La quantité ne peut pas être négative")
    private double quantite;

    @NotNull(message = "La récolte associée est requise")
    @ManyToOne
    private Recolte recolte;

    @ManyToOne
    @NotNull(message = "L'arbre associé est requis")
    private Arbre arbre;
}
