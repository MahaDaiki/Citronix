package com.citronix.service.impl;

import com.citronix.dto.ArbreDto;
import com.citronix.entity.Arbre;
import com.citronix.entity.Champ;
import com.citronix.mapper.ArbreMapper;
import com.citronix.repository.ArbreRepository;
import com.citronix.repository.ChampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ArbreServiceImplTest {

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampRepository champRepository;

    @Mock
    private ArbreMapper arbreMapper;

    @InjectMocks
    private ArbreServiceImpl arbreService;

    private ArbreDto arbreDto;
    private Arbre arbre;
    private Champ champ;

    @BeforeEach
    void setUp() {
        champ = new Champ();
        champ.setId(1);
        champ.setNom("Champ A");

        arbreDto = new ArbreDto();
        arbreDto.setId(1);
        arbreDto.setDatePlantation(LocalDate.of(2015, 1, 1));
        arbreDto.setChampId(1);

        arbre = new Arbre();
        arbre.setId(1);
        arbre.setDatePlantation(LocalDate.of(2015, 1, 1));
        arbre.setChamp(champ);
    }

//    @Test
//    void addArbre_ShouldReturnArbreDto() {
//
//        when(champRepository.findById(anyInt())).thenReturn(Optional.of(champ));
//        when(arbreMapper.toEntity(any(ArbreDto.class))).thenReturn(arbre);
//        when(arbreRepository.save(any(Arbre.class))).thenReturn(arbre);
//        when(arbreMapper.toDto(any(Arbre.class))).thenReturn(arbreDto);
//
//
//        ArbreDto result = arbreService.addArbre(arbreDto);
//
//        assertNotNull(result);
//        assertEquals(1, result.getId());
//        assertEquals(1, result.getChampId());
//        verify(champRepository).findById(anyInt());
//        verify(arbreRepository).save(any(Arbre.class));
//    }

    @Test
    void getArbreParId_ShouldReturnArbreDto() {

        when(arbreRepository.findById(anyInt())).thenReturn(Optional.of(arbre));
        when(arbreMapper.toDto(any(Arbre.class))).thenReturn(arbreDto);


        ArbreDto result = arbreService.getArbreParId(1);


        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getChampId());
    }

//    @Test
//    void deleteArbre_ShouldRemoveArbre() {
//
//        when(arbreRepository.findById(anyInt())).thenReturn(Optional.of(arbre));
//        doNothing().when(arbreRepository).deleteById(anyInt());
//        when(champRepository.save(any(Champ.class))).thenReturn(champ);
//
//        arbreService.deleteArbre(1);
//
//
//        verify(arbreRepository).deleteById(anyInt());
//        verify(champRepository).save(any(Champ.class));
//    }

    @Test
    void calculerAgeEtProductivite_ShouldCalculateCorrectly() {
        Arbre arbre = new Arbre();
        arbre.setDatePlantation(LocalDate.of(2015, 1, 1));

        arbreService.calculerAgeEtProductivite(arbre);

        assertEquals(9, arbre.getAge());
        assertEquals(12.0, arbre.getProductivite(), 0.1);
    }

    @Test
    void addArbre_ShouldThrowExceptionIfChampNotFound() {
        when(champRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> arbreService.addArbre(arbreDto));
        assertEquals("Champ non trouvé avec l'ID 1", thrown.getMessage());
    }
}
