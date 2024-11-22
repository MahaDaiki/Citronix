package com.citronix.service.interfaces;

import com.citronix.dto.VenteDto;

import java.util.List;

public interface VenteServiceInt {
    VenteDto addVente(VenteDto venteDto);
    VenteDto updateVente(int id, VenteDto venteDto);
    void deleteVente(int id);
    VenteDto getVenteById(int id);
    List<VenteDto> getAllVentes();
}
