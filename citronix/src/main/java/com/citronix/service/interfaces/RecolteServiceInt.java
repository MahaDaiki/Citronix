package com.citronix.service.interfaces;

import com.citronix.dto.RecolteDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecolteServiceInt {
    RecolteDto addRecolte(RecolteDto recolteDto);
    Page<RecolteDto> getAllRecoltes(int pageNum, int pageSize);
    RecolteDto getRecolteById(int id);
    List<RecolteDto> getRecoltesBySaison(String saison);
    void deleteRecolte(int id);
}
