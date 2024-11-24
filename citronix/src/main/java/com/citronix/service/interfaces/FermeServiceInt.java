package com.citronix.service.interfaces;

import com.citronix.dto.ChampDto;
import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FermeServiceInt {
    FermeDto addFerme(FermeDto fermeDTO);
    Page<FermeDto> getAllFermes(int pageNum, int pageSize);
    FermeDto getFermeById(int id);
    FermeDto updateFerme(int id, FermeDto fermeDTO);
    void deleteFerme(int id);
    List<FermeDto> findByCriteria(FermeSearchCriteria criteria);

}
