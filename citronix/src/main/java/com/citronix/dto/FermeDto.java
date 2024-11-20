package com.citronix.dto;

import com.citronix.entity.Ferme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FermeDto {
    private int id;
    private String nom;
    private String localisation;
    private double superficie;
    private LocalDate dateCreation;

    public FermeDto toDTO(Ferme ferme) {
        if (ferme == null) {
            return null;
        }
        return new FermeDto(
                ferme.getId(),
                ferme.getNom(),
                ferme.getLocalisation(),
                ferme.getSuperficie(),
                ferme.getDateCreation()
        );
    }

    public Ferme toEntity() {
        Ferme ferme = new Ferme();
        ferme.setId(this.id);
        ferme.setNom(this.nom);
        ferme.setLocalisation(this.localisation);
        ferme.setSuperficie(this.superficie);
        ferme.setDateCreation(this.dateCreation);
        return ferme;
    }
}
