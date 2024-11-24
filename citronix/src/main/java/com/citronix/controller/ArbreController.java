package com.citronix.controller;


import com.citronix.service.interfaces.ArbreServiceInt;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
            public ResponseEntity<ArbreDto> addArbre(@Valid @RequestBody ArbreDto arbreDto) {
                ArbreDto arbre = arbreService.addArbre(arbreDto);
                return ResponseEntity.ok(arbre);
            }


            @GetMapping
            public ResponseEntity<Page<ArbreDto>> getAllArbres(
                    @RequestParam(defaultValue = "0") int pageNum,
                    @RequestParam(defaultValue = "2") int pageSize) {

                Page<ArbreDto> arbres = arbreService.getAllArbres(pageNum, pageSize);

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
