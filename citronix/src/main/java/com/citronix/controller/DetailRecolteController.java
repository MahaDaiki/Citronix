package com.citronix.controller;

import com.citronix.dto.DetailRecolteDto;
import com.citronix.service.interfaces.DetailRecolteServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detailrecolte")
public class DetailRecolteController {
    @Autowired
    private DetailRecolteServiceInt detailRecolteService;

    @PostMapping("add")
    public ResponseEntity<DetailRecolteDto> addDetailRecolte(@Valid @RequestBody DetailRecolteDto detailRecolteDto) {
        DetailRecolteDto result = detailRecolteService.addDetailRecolte(detailRecolteDto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/recolte/{recolteId}")
    public ResponseEntity<List<DetailRecolteDto>> getDetailsByRecolte(@PathVariable int recolteId) {
        List<DetailRecolteDto> details = detailRecolteService.getDetailsByRecolte(recolteId);
        if (details.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(details);
    }
}
