package com.citronix.controller;



import com.citronix.dto.RecolteDto;
import com.citronix.service.interfaces.RecolteServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recoltes")
public class RecolteController {

    @Autowired
    private RecolteServiceInt recolteService;

    @PostMapping("/add")
    public ResponseEntity<RecolteDto> addRecolte(@Valid @RequestBody RecolteDto recolteDto) {
        RecolteDto result = recolteService.addRecolte(recolteDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecolteDto> getRecolteById(@PathVariable int id) {
        RecolteDto recolte = recolteService.getRecolteById(id);
        return ResponseEntity.ok(recolte);
    }

    @GetMapping
    public ResponseEntity<List<RecolteDto>> getAllRecoltes() {
        List<RecolteDto> recoltes = recolteService.getAllRecoltes();
        return ResponseEntity.ok(recoltes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecolte(@PathVariable int id) {
        recolteService.deleteRecolte(id);
        return ResponseEntity.noContent().build();
    }
}