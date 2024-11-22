package com.citronix.mapper;

import com.citronix.dto.VenteDto;
import com.citronix.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel  ="spring")
public interface VenteMapper {
    @Mapping(target = "recolteId", source = "recolte.id")
    VenteDto toDto(Vente vente);

    @Mapping(target = "recolte", ignore = true)
    Vente toEntity(VenteDto venteDto);
}
