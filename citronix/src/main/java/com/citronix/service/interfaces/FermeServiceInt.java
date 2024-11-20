package com.citronix.service.interfaces;

import com.citronix.dto.ChampDto;
import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;

import java.util.List;

public interface FermeServiceInt {
    FermeDto addFerme(FermeDto fermeDTO);
    List<FermeDto> getAllFermes();
    FermeDto getFermeById(int id);
    FermeDto updateFerme(int id, FermeDto fermeDTO);
    void deleteFerme(int id);
    List<FermeDto> findByCriteria(FermeSearchCriteria criteria);

}
