package com.citronix.service.impl;

import com.citronix.Validator.Validator;
import com.citronix.dto.ChampDto;
import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import com.citronix.exception.ResourceNotFoundException;
import com.citronix.mapper.ChampMapper;
import com.citronix.repository.ChampRepository;
import com.citronix.repository.FermeRepository;
import com.citronix.service.interfaces.ChampServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ChampServiceImpl implements ChampServiceInt {
    @Autowired
    private ChampRepository champRepository;

    @Autowired
    private FermeRepository fermeRepository;

    @Autowired
    private ChampMapper champMapper;

    @Override
    public ChampDto addChamp(ChampDto champDto) {
        Ferme ferme = fermeRepository.findById(champDto.getFermeId())
                .orElseThrow(() -> new ResourceNotFoundException("Ferme not found"));

        List<Champ> champs = ferme.getChamps();

        if (champs == null) {
            champs = new ArrayList<>();
        }
        Validator.validateSuperficie(champDto.getSuperficie(), champs);
        Validator.validateChampSuperficie(champDto.getSuperficie());
        Champ champ = champMapper.toEntity(champDto);

        champ.setFerme(ferme);
        Champ savedChamp = champRepository.save(champ);

        champs.add(savedChamp);
        ferme.setChamps(champs);
        fermeRepository.save(ferme);

        return champMapper.toDto(savedChamp);
    }


    @Override
    public List<ChampDto> getAllChamps() {
        List<Champ> champs = champRepository.findAll();
        List<ChampDto> champDtos = new ArrayList<>();
        for (Champ champ : champs) {
            champDtos.add(champMapper.toDto(champ));
        }
        return champDtos;
    }

    @Override
    public ChampDto getChampById(int id) {
        Champ champ = champRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Champ not found"));
        return champMapper.toDto(champ);
    }

    @Override
    public void deleteChamp(int id) {
        Champ champ = champRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Champ not found"));
        champRepository.delete(champ);
    }

    @Override
    public ChampDto updateChamp(int id, ChampDto champDto) {
        Champ existingChamp = champRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Champ with ID " + id + " not found"));

        System.out.println("Updating Champ with ID: " + id);
        System.out.println("ChampDto: " + champDto);


        if (champDto.getSuperficie() > 0) {
            Validator.validateChampSuperficie(champDto.getSuperficie());
            existingChamp.setSuperficie(champDto.getSuperficie());
        }


        if (champDto.getNom() != null) {
            existingChamp.setNom(champDto.getNom());
        }

        if (champDto.getFermeId() > 0) {
            Ferme ferme = fermeRepository.findById(champDto.getFermeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ferme with ID " + champDto.getFermeId() + " not found"));


            List<Champ> champs = ferme.getChamps();
            if (champs == null) {
                champs = new ArrayList<>();
            }

            if (champs.size() >= 10) {
                throw new IllegalArgumentException("This farm already has 10 champs. You cannot add more.");
            }

            existingChamp.setFerme(ferme);
        }

        Champ updatedChamp = champRepository.save(existingChamp);


        return champMapper.toDto(updatedChamp);
    }

    @Override
    public List<ChampDto> getChampsByFermeId(int fermeId) {

        Ferme ferme = fermeRepository.findById(fermeId)
                .orElseThrow(() -> new ResourceNotFoundException("Ferme not found with ID: " + fermeId));

        List<Champ> champs = ferme.getChamps();

        List<ChampDto> champDtos = new ArrayList<>();
        for (Champ champ : champs) {
            champDtos.add(champMapper.toDto(champ));
        }
        return champDtos;
    }

}
