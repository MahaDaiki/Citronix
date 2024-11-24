package com.citronix.service.interfaces;

import com.citronix.dto.ArbreDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArbreServiceInt {
    ArbreDto addArbre(ArbreDto arbreDto);
    Page<ArbreDto> getAllArbres(int pageNum, int pageSize);
    ArbreDto getArbreParId(int id);
    List<ArbreDto> getArbresParChampId(int champId);
    void deleteArbre(int id);
    void updateAgesEtProductivite();
}
