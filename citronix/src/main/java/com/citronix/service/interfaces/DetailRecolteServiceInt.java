package com.citronix.service.interfaces;

import com.citronix.dto.DetailRecolteDto;

import java.util.List;

public interface DetailRecolteServiceInt {
    DetailRecolteDto addDetailRecolte(DetailRecolteDto detailRecolteDto);
    List<DetailRecolteDto> getDetailsByRecolte(int recolteId);
}
