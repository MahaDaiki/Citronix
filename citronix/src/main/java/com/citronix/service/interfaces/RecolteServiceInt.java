package com.citronix.service.interfaces;

import com.citronix.dto.RecolteDto;

import java.util.List;

public interface RecolteServiceInt {
    RecolteDto addRecolte(RecolteDto recolteDto);
    List<RecolteDto> getAllRecoltes();
    RecolteDto getRecolteById(int id);
    List<RecolteDto> getRecoltesBySaison(String saison);
    void deleteRecolte(int id);
}
