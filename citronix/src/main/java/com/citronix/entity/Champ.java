package com.citronix.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Champ {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le nom du champ est requis")
    private String nom;

    @NotNull(message = "La superficie est requise")
    @Min(value = 1000, message = "La superficie du champ doit être au moins 1000 m² (0.1 hectare).")
    private double superficie;

    @ManyToOne
    @JoinColumn(name = "ferme_id")
    @NotEmpty(message = "La ferme associée ne peut pas être vide")
    private Ferme ferme;


    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Arbre> arbres;

}
