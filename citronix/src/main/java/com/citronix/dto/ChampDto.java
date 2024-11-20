package com.citronix.dto;


import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChampDto {

    private int id;

    @NotBlank(message = "Le nom du champ est requis")
    private String nom;

    @NotNull(message = "La superficie est requise")
    @Min(value = 1000, message = "La superficie du champ doit être au moins 1000 m² (0.1 hectare).")
    private double superficie;

    @NotNull(message = "La ferme associée est requise")
    private int fermeId;

    public Champ toEntity(Ferme ferme) {
        Champ champ = new Champ();
        champ.setId(this.id);
        champ.setNom(this.nom);
        champ.setSuperficie(this.superficie);
        champ.setFerme(ferme);
        return champ;
    }


    public static ChampDto toDto(Champ champ) {
        ChampDto dto = new ChampDto();
        dto.setId(champ.getId());
        dto.setNom(champ.getNom());
        dto.setSuperficie(champ.getSuperficie());
        if (champ.getFerme() != null) {
            dto.setFermeId(champ.getFerme().getId());  // Set the ID of the Ferme from the Champ entity
        }
        return dto;
    }
}
