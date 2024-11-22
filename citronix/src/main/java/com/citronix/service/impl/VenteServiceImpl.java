package com.citronix.service.impl;

import com.citronix.dto.VenteDto;
import com.citronix.entity.Recolte;
import com.citronix.entity.Vente;
import com.citronix.mapper.VenteMapper;
import com.citronix.Validator.Validator;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import com.citronix.service.interfaces.VenteServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenteServiceImpl implements VenteServiceInt {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private RecolteRepository recolteRepository;

    @Autowired
    private VenteMapper venteMapper;



    @Override
    public VenteDto addVente(VenteDto venteDto) {
        Vente vente = venteMapper.toEntity(venteDto);

        Recolte recolte = recolteRepository.findById(venteDto.getRecolteId())
                .orElseThrow(() -> new RuntimeException("Récolte introuvable"));

        vente.setRecolte(recolte);

        double revenu = venteDto.getQuantite() * venteDto.getPrixUnitaire();
        vente.setRevenu(revenu);

        Vente savedVente = venteRepository.save(vente);

        recolte.getVentes().add(savedVente);
        recolteRepository.save(recolte);

        return venteMapper.toDto(savedVente);
    }


    @Override
    public VenteDto updateVente(int id, VenteDto venteDto) {

        Vente existingVente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente not found"));


        existingVente.setQuantite(venteDto.getQuantite());
        existingVente.setPrixUnitaire(venteDto.getPrixUnitaire());
        existingVente.setDate(venteDto.getDate());


        double revenu = existingVente.getQuantite() * existingVente.getPrixUnitaire();
        existingVente.setRevenu(revenu);

        Vente updatedVente = venteRepository.save(existingVente);


        Recolte recolte = existingVente.getRecolte();
        Validator.validateRecolteTotalQuantity(recolte, recolte.getVentes());

        return venteMapper.toDto(updatedVente);
    }

    @Override
    public void deleteVente(int id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente not found"));

        Recolte recolte = vente.getRecolte();
        recolte.getVentes().remove(vente);
        recolteRepository.save(recolte);


        venteRepository.delete(vente);



    }

    @Override
    public VenteDto getVenteById(int id) {

        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vente not found"));

        return venteMapper.toDto(vente);
    }

    @Override
    public List<VenteDto> getAllVentes() {

        List<Vente> ventes = venteRepository.findAll();
        return ventes.stream().map(venteMapper::toDto).collect(Collectors.toList());
    }
}
