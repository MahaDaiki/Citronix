package com.citronix.mapper;

import com.citronix.dto.FermeDto;
import com.citronix.entity.Ferme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FermeMapper {

    FermeDto toDto(Ferme ferme);

//    @Mapping(target = "champs", ignore = true)
    Ferme toEntity(FermeDto fermeDto);
}