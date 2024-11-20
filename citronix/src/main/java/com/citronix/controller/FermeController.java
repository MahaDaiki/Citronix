package com.citronix.controller;


import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import com.citronix.service.interfaces.FermeServiceInt;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fermes")
public class FermeController {
    @Autowired
    private FermeServiceInt fermeService;

    @PostMapping("/add")
    public ResponseEntity<FermeDto> addFerme(@Valid @RequestBody FermeDto fermeDto) {
        FermeDto result = fermeService.addFerme(fermeDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<FermeDto>> getAllFermes() {
        List<FermeDto> fermes = fermeService.getAllFermes();
        return ResponseEntity.ok(fermes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FermeDto> getFerme(@PathVariable int id) {
        FermeDto ferme = fermeService.getFermeById(id);
        return ResponseEntity.ok(ferme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FermeDto> updateFerme(@PathVariable int id,@Valid @RequestBody FermeDto fermeDTO) {
        FermeDto updatedFerme = fermeService.updateFerme(id, fermeDTO);
        return ResponseEntity.ok(updatedFerme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFerme(@PathVariable int id) {
        fermeService.deleteFerme(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public List<FermeDto> searchFermes(@RequestBody FermeSearchCriteria criteria) {
        List<FermeDto> fermeDtos = fermeService.findByCriteria(criteria);
        return ResponseEntity.ok(fermeDtos).getBody();
    }


}
