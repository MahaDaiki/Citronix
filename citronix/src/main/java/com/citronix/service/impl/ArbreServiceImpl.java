package com.citronix.service.impl;

import com.citronix.Validator.Validator;
import com.citronix.dto.ArbreDto;
import com.citronix.entity.Arbre;
import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import com.citronix.mapper.ArbreMapper;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import com.citronix.service.interfaces.ArbreServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArbreServiceImpl implements ArbreServiceInt {

    @Autowired
    private ArbreRepository arbreRepository;

    @Autowired
    private ChampRepository champRepository;

    @Autowired
    private ArbreMapper arbreMapper;

    @Override
    public ArbreDto addArbre(ArbreDto arbreDto) {


        Champ champ = champRepository.findById(arbreDto.getChampId())
                .orElseThrow(() -> new RuntimeException("Champ non trouvé avec l'ID " + arbreDto.getChampId()));
        Ferme ferme = champ.getFerme();

        if (champ.getArbres() == null) {
            champ.setArbres(new ArrayList<>());
        }

        Validator.validateDatePlantation(ferme, arbreDto.getDatePlantation());
        Validator.validatePlantingPeriod(arbreDto.getDatePlantation());
        Validator.validateTreeDensity(champ, champ.getArbres().size() + 1);


        Arbre arbre = arbreMapper.toEntity(arbreDto);
        champ.getArbres().add(arbre);
        arbre.setChamp(champ);

        calculerAgeEtProductivite(arbre);
        arbreRepository.save(arbre);
        champRepository.save(champ);

        return arbreMapper.toDto(arbre);
    }

    @Override
    public Page<ArbreDto> getAllArbres(int pageNum, int pageSize) {
        Page<Arbre> arbrePage = arbreRepository.findAll(PageRequest.of(pageNum, pageSize));
        arbrePage.getContent().forEach(this::calculerAgeEtProductivite);

        return arbrePage.map(arbreMapper::toDto);
    }

    @Override
    public ArbreDto getArbreParId(int id) {
        Arbre arbre = arbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbre non trouvé avec l'ID " + id));
        calculerAgeEtProductivite(arbre);
        return arbreMapper.toDto(arbre);
    }

    @Override
    public List<ArbreDto> getArbresParChampId(int champId) {
        List<Arbre> arbres = arbreRepository.findByChampId(champId);
        return arbres.stream().map(arbreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteArbre(int id) {
        Arbre arbre = arbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbre non trouvé avec l'ID " + id));

        Champ champ = arbre.getChamp();

        if (champ != null && champ.getArbres() != null) {
            champ.getArbres().remove(arbre);
            champRepository.save(champ);
        }

        arbreRepository.deleteById(id);
    }

    @Override
    public void updateAgesEtProductivite() {
        List<Arbre> arbres = arbreRepository.findAll();
        for (Arbre arbre : arbres) {
            calculerAgeEtProductivite(arbre);
        }
        arbreRepository.saveAll(arbres);
    }

    public void calculerAgeEtProductivite(Arbre arbre) {
        LocalDate dateActuelle = LocalDate.now();
        LocalDate datePlantation = arbre.getDatePlantation();

        System.out.println("Date de plantation: " + datePlantation);

        int age = (int) ChronoUnit.YEARS.between(datePlantation, dateActuelle);
        arbre.setAge(age);

        if (age < 3) {
            arbre.setProductivite(2.5);
        } else if (age <= 10) {
            arbre.setProductivite(12.0);
        }  else if (age <= 20) {
            arbre.setProductivite(20.0);
        } else {
            arbre.setProductivite(0.0);
        }
    }
}
