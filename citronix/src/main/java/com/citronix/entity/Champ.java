package com.citronix.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @DecimalMin(value = "0.1", message = "La superficie d'un champ doit être au minimum de 0.1 hectare")
    private double superficie;

    @ManyToOne
    @NotEmpty(message = "La ferme associée ne peut pas être vide")
    private Ferme ferme;


}