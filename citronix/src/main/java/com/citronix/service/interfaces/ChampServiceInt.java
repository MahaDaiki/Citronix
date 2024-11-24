package com.citronix.service.interfaces;

import com.citronix.dto.ChampDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChampServiceInt {
    ChampDto addChamp(ChampDto champDto);
    Page<ChampDto> getAllChamps(int pageNum, int pageSize);
    ChampDto getChampById(int id);
    void deleteChamp(int id);
    ChampDto updateChamp(int id, ChampDto champDto);
    List<ChampDto> getChampsByFermeId(int fermeId);
}
