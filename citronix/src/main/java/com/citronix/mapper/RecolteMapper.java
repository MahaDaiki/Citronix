package com.citronix.mapper;


import com.citronix.dto.RecolteDto;
import com.citronix.entity.Recolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecolteMapper {
//    @Mapping(target = "id", ignore = true)
//@Mapping(target = "arbres", ignore = true)
    RecolteDto toDto(Recolte recolte);

//    @Mapping(target = "arbres", source = "arbresIds")
    Recolte toEntity(RecolteDto recolteDto);
}