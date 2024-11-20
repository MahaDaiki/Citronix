package com.citronix.repository;

import com.citronix.dto.ChampDto;
import com.citronix.entity.Champ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChampRepository extends JpaRepository<Champ, Integer> {
    List<ChampDto> getChampsByFermeId(int fermeId);

}
