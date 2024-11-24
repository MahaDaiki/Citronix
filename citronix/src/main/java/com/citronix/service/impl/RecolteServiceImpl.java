package com.citronix.service.impl;


import com.citronix.Validator.Validator;
import com.citronix.dto.RecolteDto;
import com.citronix.entity.Arbre;
import com.citronix.entity.Detail_recolte;
import com.citronix.entity.Recolte;
import com.citronix.entity.Saison;
import com.citronix.mapper.RecolteMapper;
import org.springframework.data.domain.PageRequest;
import com.citronix.repository.DetailRecolteRepository;
import com.citronix.repository.RecolteRepository;
import com.citronix.service.interfaces.RecolteServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecolteServiceImpl implements RecolteServiceInt {

    @Autowired
    private RecolteRepository recolteRepository;

    @Autowired
    private DetailRecolteRepository detailRecolteRepository;

    @Autowired
    private RecolteMapper recolteMapper;

    @Override
    public RecolteDto addRecolte(RecolteDto recolteDto) {
        Validator.validateHarvestDate(recolteDto.getDateRecolte());

        Saison season = determineSeason(recolteDto.getDateRecolte());
        Recolte recolte = recolteMapper.toEntity(recolteDto);

        recolte.setSaison(season);
        recolte.setQuantiteTotal(0.0);
        recolte = recolteRepository.save(recolte);
        return recolteMapper.toDto(recolte);
    }

    @Override
    public Page<RecolteDto> getAllRecoltes(int pageNum, int pageSize) {
        Page<Recolte> recoltePage = recolteRepository.findAll(PageRequest.of(pageNum, pageSize));

            return recoltePage.map(recolteMapper::toDto);

    }

    @Override
    public RecolteDto getRecolteById(int id) {
        Recolte recolte = recolteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recolte not found with ID: " + id));
        return recolteMapper.toDto(recolte);
    }

    @Override
    public List<RecolteDto> getRecoltesBySaison(String saison) {
        Saison saisonEnum = Saison.valueOf(saison.toUpperCase());
        return recolteRepository.findBySaison(saisonEnum).stream()
                .map(recolteMapper::toDto)
                .toList();
    }

    @Override
    public void deleteRecolte(int id) {
        if (!recolteRepository.existsById(id)) {
            throw new RuntimeException("Recolte not found with ID: " + id);
        }
        recolteRepository.deleteById(id);
    }

    private void validateTreeNotInAnotherHarvest(Saison saison, Arbre arbre) {
        List<Detail_recolte> existingDetails = detailRecolteRepository.findByArbreId(arbre.getId());
        for (Detail_recolte detail : existingDetails) {
            if (detail.getRecolte().getSaison().equals(saison)) {
                throw new RuntimeException("Arbre ID " + arbre.getId() + " is already harvested in the same season.");
            }
        }
    }

    private Saison determineSeason(LocalDate dateRecolte) {
        int month = dateRecolte.getMonthValue();

        if (month >= 3 && month <= 5) {
            return Saison.Printemps;
        } else if (month >= 6 && month <= 8) {
            return Saison.Ete;
        } else if (month >= 9 && month <= 11) {
            return Saison.Automne;
        } else {
            return Saison.Hiver;
        }
    }

}
