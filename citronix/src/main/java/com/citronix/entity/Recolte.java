package com.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Recolte {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "La date de récolte est requise")
    private LocalDate dateRecolte;

    @PositiveOrZero(message = "La quantité totale ne peut pas être négative")
    private double quantiteTotal;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    @NotNull(message = "La liste des détails de récolte ne peut pas être nulle")
    private List<DetailRecolte> detailRecoltes;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<Vente> ventes;
}
