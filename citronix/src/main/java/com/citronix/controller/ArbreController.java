package com.citronix.controller;


import com.citronix.service.interfaces.ArbreServiceInt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.citronix.dto.ArbreDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arbres")
public class ArbreController {
        @Autowired
        private ArbreServiceInt  arbreService;


            @PostMapping("/add")
            public ResponseEntity<ArbreDto> addArbre(@RequestBody ArbreDto arbreDto) {
                ArbreDto arbre = arbreService.addArbre(arbreDto);
                return ResponseEntity.ok(arbre);
            }


            @GetMapping
            public ResponseEntity<List<ArbreDto>> getAllArbres() {
                List<ArbreDto> arbres = arbreService.getAllArbres();
                return ResponseEntity.ok(arbres);
            }


            @GetMapping("/{id}")
            public ResponseEntity<ArbreDto> gettArbreParId(@PathVariable int id) {
                ArbreDto arbre = arbreService.getArbreParId(id);
                return ResponseEntity.ok(arbre);
            }


            @GetMapping("/champ/{champId}")
            public ResponseEntity<List<ArbreDto>> gettArbresParChampId(@PathVariable int champId) {
                List<ArbreDto> arbres = arbreService.getArbresParChampId(champId);
                return ResponseEntity.ok(arbres);
            }


            @DeleteMapping("/{id}")
            public ResponseEntity<Void> deleteArbre(@PathVariable int id) {
                arbreService.deleteArbre(id);
                return ResponseEntity.noContent().build();
            }


            @PutMapping("/update-productivite")
            public ResponseEntity<Void> updateAgesEtProductivite() {
                arbreService.updateAgesEtProductivite();
                return ResponseEntity.noContent().build();
            }
}
