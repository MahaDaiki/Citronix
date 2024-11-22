package com.citronix.controller;

import com.citronix.dto.DetailRecolteDto;
import com.citronix.service.interfaces.DetailRecolteServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detail-recolte")
public class DetailRecolteController {
    @Autowired
    private DetailRecolteServiceInt detailRecolteService;

    @PostMapping
    public DetailRecolteDto addDetailRecolte(@Valid @RequestBody DetailRecolteDto detailRecolteDto) {
        return detailRecolteService.addDetailRecolte(detailRecolteDto);
    }

    @GetMapping("/recolte/{recolteId}")
    public List<DetailRecolteDto> getDetailsByRecolte(@PathVariable int recolteId) {
        return detailRecolteService.getDetailsByRecolte(recolteId);
    }
}
