package com.citronix.repository;

import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;

import java.util.List;

public interface CustomFermeRepository {
    List<FermeDto> findByCriteria(FermeSearchCriteria criteria);
}
