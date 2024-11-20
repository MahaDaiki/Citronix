package com.citronix.service.impl;

import com.citronix.Validator.Validator;
import com.citronix.dto.ChampDto;
import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import com.citronix.entity.Ferme;
import com.citronix.exception.ResourceNotFoundException;
import com.citronix.repository.FermeRepository;
import com.citronix.service.interfaces.FermeServiceInt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FermeServiceImpl implements FermeServiceInt {
    @Autowired
    private FermeRepository fermeRepository;



    @Override
    public FermeDto addFerme(FermeDto fermeDTO) {
        Validator.validateDateCreation(fermeDTO.getDateCreation());
        Validator.validateFermeSuperficie(fermeDTO.getSuperficie());
        Ferme ferme = fermeDTO.toEntity();
        Ferme savedFerme = fermeRepository.save(ferme);
        return new FermeDto().toDTO(savedFerme);
    }

    @Override
    public List<FermeDto> getAllFermes() {
        List<Ferme> fermeList = fermeRepository.findAll();
        List<FermeDto> fermeDtoList = new ArrayList<>();
        for (Ferme ferme : fermeList) {
            fermeDtoList.add(new FermeDto().toDTO(ferme));
        }
        return fermeDtoList;
    }

    @Override
    public FermeDto getFermeById(int id) {
        Optional<Ferme> ferme = fermeRepository.findById(id);
        if(ferme.isPresent()) {
            return new FermeDto().toDTO(ferme.get());
        }
        else {
            throw new ResourceNotFoundException("Ferme not found");
        }
    }

    @Override
    public FermeDto updateFerme(int id, FermeDto fermeDTO) {
        Ferme existingFerme = fermeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ferme with ID " + id + " not found"));


        Validator.validateFermeSuperficie(fermeDTO.getSuperficie());

        if (fermeDTO.getNom() != null) {
            existingFerme.setNom(fermeDTO.getNom());
        }
        if (fermeDTO.getLocalisation() != null) {
            existingFerme.setLocalisation(fermeDTO.getLocalisation());
        }
        if (fermeDTO.getSuperficie() != 0) {
            existingFerme.setSuperficie(fermeDTO.getSuperficie());
        }
        if (fermeDTO.getDateCreation() != null) {
            existingFerme.setDateCreation(fermeDTO.getDateCreation());
        }


        Ferme updatedFerme = fermeRepository.save(existingFerme);
        return new FermeDto().toDTO(updatedFerme);
    }

    @Override
    public void deleteFerme(int id) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ferme with ID " + id + " not found"));
        fermeRepository.delete(ferme);
    }

    @Override
    public List<FermeDto> findByCriteria(FermeSearchCriteria criteria) {
        return fermeRepository.findByCriteria(criteria);
    }


}
