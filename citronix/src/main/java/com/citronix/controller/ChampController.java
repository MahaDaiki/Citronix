package com.citronix.controller;


import com.citronix.dto.ChampDto;
import com.citronix.repository.ChampRepository;
import com.citronix.service.interfaces.ChampServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Channel;
import java.util.List;

@RestController
@RequestMapping("/api/champs")
public class ChampController {

    @Autowired
    private ChampServiceInt champService;


    @PostMapping("/add")
    public ResponseEntity<ChampDto> addChamp(@Valid @RequestBody ChampDto champDto) {
        ChampDto result = champService.addChamp(champDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ChampDto>> getAllChamps() {
        List<ChampDto> champs = champService.getAllChamps();
        System.out.println("ChampDto: " +  champs);
        return ResponseEntity.ok(champs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChampDto> getChamp(@PathVariable int id) {
        ChampDto champ = champService.getChampById(id);
        return ResponseEntity.ok(champ);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChampDto> updateChamp(@PathVariable int id, @Valid @RequestBody ChampDto champDto) {
        System.out.println("Ferme ID in request: " + champDto.getFermeId());

        ChampDto updatedChamp = champService.updateChamp(id, champDto);
        return ResponseEntity.ok(updatedChamp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChamp(@PathVariable int id) {
        champService.deleteChamp(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ferme/{fermeId}")
    public ResponseEntity<List<ChampDto>> getChampsByFermeId(@PathVariable int fermeId) {
        List<ChampDto> champDtos = champService.getChampsByFermeId(fermeId);
        return ResponseEntity.ok(champDtos);
    }
}
