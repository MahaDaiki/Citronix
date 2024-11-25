package com.citronix.controller;


import com.citronix.dto.VenteDto;
import com.citronix.service.interfaces.VenteServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
public class VenteController {
    @Autowired
    private VenteServiceInt venteService;


    @PostMapping("/add")
    public ResponseEntity<VenteDto> addVente(@Valid @RequestBody VenteDto venteDto) {
        VenteDto savedVente = venteService.addVente(venteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVente);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<VenteDto> updateVente(@PathVariable int id, @RequestBody VenteDto venteDto) {
        VenteDto updatedVente = venteService.updateVente(id, venteDto);
        return ResponseEntity.ok(updatedVente);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable int id) {
        venteService.deleteVente(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<VenteDto> getVenteById(@PathVariable int id) {
        VenteDto venteDto = venteService.getVenteById(id);
        return ResponseEntity.ok(venteDto);
    }


    @GetMapping
    public ResponseEntity<Page<VenteDto>> getAllVentes(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "2") int pageSize) {

        Page<VenteDto> ventes = venteService.getAllVentes(pageNum, pageSize);

        return ResponseEntity.ok(ventes);
    }
}
