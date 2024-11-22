package com.citronix.mapper;

import com.citronix.dto.DetailRecolteDto;
import com.citronix.entity.Detail_recolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetailRecolteMapper {

    @Mapping(source = "recolte.id", target = "recolteId")
    @Mapping(source = "arbre.id", target = "arbreId")
    DetailRecolteDto toDto(Detail_recolte detailRecolte);

    @Mapping(target = "recolte", ignore = true)
    @Mapping(target = "arbre", ignore = true)
    Detail_recolte toEntity(DetailRecolteDto detailRecolteDto);
}