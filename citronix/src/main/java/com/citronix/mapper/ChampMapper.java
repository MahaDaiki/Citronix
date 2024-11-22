package com.citronix.mapper;

import com.citronix.dto.ChampDto;
import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ChampMapper {

    @Mapping(source = "ferme.id", target = "fermeId")
    ChampDto toDto(Champ champ);

    @Mapping(target = "ferme", ignore = true)
    Champ toEntity(ChampDto champDto);



}
