package com.citronix.mapper;

import com.citronix.dto.ArbreDto;
import com.citronix.entity.Arbre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArbreMapper {
   @Mapping(source = "champ.id", target = "champId")
    ArbreDto toDto(Arbre arbre);

    @Mapping(target = "champ", ignore = true)
    Arbre toEntity(ArbreDto arbreDto);
}
