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
public class Detail_recolte {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @PositiveOrZero(message = "La quantité ne peut pas être négative")
    private double quantite;

    @ManyToOne
    @NotNull(message = "La récolte associée est requise")
    private Recolte recolte;

    @ManyToOne
    @NotNull(message = "L'arbre associé est requis")
    private Arbre arbre;
}
