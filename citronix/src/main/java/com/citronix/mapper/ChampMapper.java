package com.citronix.mapper;

import com.citronix.dto.ChampDto;
import com.citronix.entity.Champ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ChampMapper {

    @Mapping(source = "ferme.id", target = "fermeId")
    ChampDto toDto(Champ champ);

    @Mapping(target = "ferme", ignore = true)
    Champ toEntity(ChampDto champDto);



}
