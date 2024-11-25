package com.citronix.mapper;


import com.citronix.dto.RecolteDto;
import com.citronix.entity.Recolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecolteMapper {

    @Mapping(target = "venteIds", ignore = true)
    @Mapping(target = "arbresIds", ignore = true)
    RecolteDto toDto(Recolte recolte);

//    @Mapping(target = "arbres", ignore = true)
//    @Mapping(target = "ventes", ignore = true)
    Recolte toEntity(RecolteDto recolteDto);

}