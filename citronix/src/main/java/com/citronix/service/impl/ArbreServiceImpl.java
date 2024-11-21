package com.citronix.service.impl;

import com.citronix.Validator.Validator;
import com.citronix.dto.ArbreDto;
import com.citronix.entity.Arbre;
import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import com.citronix.service.interfaces.ArbreServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArbreServiceImpl implements ArbreServiceInt {

    @Autowired
    private ArbreRepository arbreRepository;

    @Autowired
    private ChampRepository champRepository;

    @Override
    public ArbreDto addArbre(ArbreDto arbreDto) {

        Champ champ = champRepository.findById(arbreDto.getChampId())
                .orElseThrow(() -> new RuntimeException("Champ non trouvé avec l'ID " + arbreDto.getChampId()));
        Ferme ferme = champ.getFerme();

        Validator.validateDatePlantation(ferme, arbreDto.getDatePlantation());
        Validator.validateTreeDensity(champ, champ.getArbres().size() + 1);

        Arbre arbre = ArbreDto.toEntity(arbreDto);
        arbre.setChamp(champ);

        calculerAgeEtProductivite(arbre);


        champ.getArbres().add(arbre);
        arbre = arbreRepository.save(arbre);

        return ArbreDto.toDto(arbre);
    }

    @Override
    public List<ArbreDto> getAllArbres() {
        List<Arbre> arbres = arbreRepository.findAll();
        for (Arbre arbre : arbres) {
            calculerAgeEtProductivite(arbre);
        }
        return arbres.stream().map(ArbreDto::toDto).collect(Collectors.toList());
    }

    @Override
    public ArbreDto getArbreParId(int id) {
        Arbre arbre = arbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbre non trouvé avec l'ID " + id));
        calculerAgeEtProductivite(arbre);
        return ArbreDto.toDto(arbre);
    }

    @Override
    public List<ArbreDto> getArbresParChampId(int champId) {
        List<Arbre> arbres = arbreRepository.findByChampId(champId);
        return arbres.stream().map(ArbreDto::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteArbre(int id) {
        Arbre arbre = arbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbre non trouvé avec l'ID " + id));

        Champ champ = arbre.getChamp();
        champ.getArbres().remove(arbre);

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

    private void calculerAgeEtProductivite(Arbre arbre) {
        LocalDate dateActuelle = LocalDate.now();
        LocalDate datePlantation = arbre.getDatePlantation();

        int age = (int) ChronoUnit.YEARS.between(datePlantation, dateActuelle);
        arbre.setAge(age);

        if (age < 3) {
            arbre.setProductivite(2.5);
        } else if (age <= 10) {
            arbre.setProductivite(12.0);
        } else {
            arbre.setProductivite(20.0);
        }
    }
}
