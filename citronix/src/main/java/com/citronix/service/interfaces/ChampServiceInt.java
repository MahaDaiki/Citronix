package com.citronix.service.interfaces;

import com.citronix.dto.ChampDto;

import java.util.List;

public interface ChampServiceInt {
    ChampDto addChamp(ChampDto champDto);
    List<ChampDto> getAllChamps();
    ChampDto getChampById(int id);
    void deleteChamp(int id);
    ChampDto updateChamp(int id, ChampDto champDto);
    List<ChampDto> getChampsByFermeId(int fermeId);
}
