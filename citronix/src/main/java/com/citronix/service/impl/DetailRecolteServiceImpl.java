package com.citronix.service.impl;
import com.citronix.Validator.Validator;
import com.citronix.dto.DetailRecolteDto;
import com.citronix.entity.Detail_recolte;
import com.citronix.entity.Arbre;
import com.citronix.entity.Recolte;
import com.citronix.mapper.DetailRecolteMapper;
import com.citronix.repository.DetailRecolteRepository;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.RecolteRepository;
import com.citronix.service.interfaces.DetailRecolteServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetailRecolteServiceImpl implements DetailRecolteServiceInt {
    @Autowired
    private DetailRecolteRepository detailRecolteRepository;

    @Autowired
    private ArbreRepository arbreRepository;

    @Autowired
    private RecolteRepository recolteRepository;

    @Autowired
    private DetailRecolteMapper detailRecolteMapper;

    @Override
    public DetailRecolteDto addDetailRecolte(DetailRecolteDto detailRecolteDto) {

        Recolte recolte = recolteRepository.findById(detailRecolteDto.getRecolteId())
                .orElseThrow(() -> new RuntimeException("Recolte not found"));

        Arbre arbre = arbreRepository.findById(detailRecolteDto.getArbreId())
                .orElseThrow(() -> new RuntimeException("Arbre not found"));



        List<Detail_recolte> existingDetails = detailRecolteRepository
                .findByArbreIdAndRecolteSaison(arbre.getId(), recolte.getSaison());

        if (!existingDetails.isEmpty()) {
            throw new RuntimeException("Arbre cannot be included in more than one recolte per season");
        }

        Validator.validatePlantingBeforeHarvest(arbre.getDatePlantation(), recolte.getDateRecolte());

        double productivity = arbre.getProductivite();
        double quantity = detailRecolteDto.getQuantite();
        if (Math.abs(quantity - productivity) > productivity * 0.1) {
            throw new RuntimeException("Quantité must be close to the arbre productivity.");
        }


        Detail_recolte detailRecolte = detailRecolteMapper.toEntity(detailRecolteDto);
        detailRecolte.setArbre(arbre);
        detailRecolte.setRecolte(recolte);

        detailRecolte = detailRecolteRepository.save(detailRecolte);

        updateRecolteQuantiteTotal(recolte);
        return detailRecolteMapper.toDto(detailRecolte);
    }

    @Override
    public List<DetailRecolteDto> getDetailsByRecolte(int recolteId) {
        List<Detail_recolte> details = detailRecolteRepository.findByRecolteId(recolteId);
        return details.stream().map(detailRecolteMapper::toDto).toList();
    }


    private void updateRecolteQuantiteTotal(Recolte recolte) {
        double totalQuantity = 0.0;
        for (Detail_recolte detail : detailRecolteRepository.findByRecolteId(recolte.getId())) {
            totalQuantity += detail.getQuantite();
        }
        recolte.setQuantiteTotal(totalQuantity);

        recolteRepository.save(recolte);
    }
}
