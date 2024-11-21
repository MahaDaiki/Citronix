package com.citronix.service.interfaces;

import com.citronix.dto.ArbreDto;

import java.util.List;

public interface ArbreServiceInt {
    ArbreDto addArbre(ArbreDto arbreDto);
    List<ArbreDto> getAllArbres();
    ArbreDto getArbreParId(int id);
    List<ArbreDto> getArbresParChampId(int champId);
    void deleteArbre(int id);
    void updateAgesEtProductivite();
}
