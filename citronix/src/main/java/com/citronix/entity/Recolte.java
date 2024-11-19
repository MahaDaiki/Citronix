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

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La saison est requise")
    private Saison saison;

    @PositiveOrZero(message = "La quantité totale ne peut pas être négative")
    private double quantiteTotal;

    @ManyToMany
    @JoinTable(
            name = "detail_recolte",
            joinColumns = @JoinColumn(name = "recolte_id"),
            inverseJoinColumns = @JoinColumn(name = "arbre_id")
    )
    @NotNull(message = "La liste des arbres associés ne peut pas être nulle")
    private List<Arbre> arbres;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<Vente> ventes;
}
