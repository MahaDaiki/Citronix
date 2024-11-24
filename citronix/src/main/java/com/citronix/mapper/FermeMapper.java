package com.citronix.mapper;

import com.citronix.dto.FermeDto;
import com.citronix.entity.Ferme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FermeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "localisation", target = "localisation")
    @Mapping(source = "superficie", target = "superficie")
    @Mapping(source = "dateCreation", target = "dateCreation")

    FermeDto toDto(Ferme ferme);


    @Mapping(target = "champs", ignore = true)
    Ferme toEntity(FermeDto fermeDto);
}