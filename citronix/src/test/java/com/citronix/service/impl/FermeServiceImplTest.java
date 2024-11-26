package com.citronix.service.impl;

import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import com.citronix.entity.Ferme;
import com.citronix.exception.ResourceNotFoundException;
import com.citronix.mapper.FermeMapper;
import com.citronix.repository.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FermeServiceImplTest {

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private FermeMapper fermeMapper;

    @InjectMocks
    private FermeServiceImpl fermeService;

    private FermeDto fermeDto;

    private Ferme ferme;

    @BeforeEach
    public void setUp() {
        fermeDto = new FermeDto();
        fermeDto.setNom("Ferme Test");
        fermeDto.setLocalisation("Localisation Test");
        fermeDto.setSuperficie(100000);
        fermeDto.setDateCreation(LocalDate.now());

        ferme = new Ferme();
        ferme.setId(1);
        ferme.setNom("Ferme Test");
        ferme.setLocalisation("Localisation Test");
        ferme.setSuperficie(100000);
        ferme.setDateCreation(LocalDate.now());
    }

    @Test
    public void testAddFerme() {
        when(fermeMapper.toEntity(any(FermeDto.class))).thenReturn(ferme);
        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);
        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(fermeDto);


        FermeDto savedFerme = fermeService.addFerme(fermeDto);

        assertNotNull(savedFerme);
        assertEquals("Ferme Test", savedFerme.getNom());
        assertEquals("Localisation Test", savedFerme.getLocalisation());
    }


    @Test
    public void testGetAllFermes() {

        List<Ferme> fermeList = List.of(ferme);
        Page<Ferme> fermePage = new PageImpl<>(fermeList);
        when(fermeRepository.findAll(PageRequest.of(0, 10))).thenReturn(fermePage);
        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(fermeDto);

        Page<FermeDto> result = fermeService.getAllFermes(0, 10);

        assert result.getTotalElements() == 1;
        assert result.getContent().get(0).getNom().equals("Ferme Test");
    }

    @Test
    public void testGetFermeById_Success() {
        when(fermeRepository.findById(org.mockito.ArgumentMatchers.anyInt())).thenReturn(Optional.of(ferme));
        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(fermeDto);

        FermeDto fermeDto = fermeService.getFermeById(1);

        assert fermeDto.getNom().equals("Ferme Test");
    }

    @Test
    public void testGetFermeById_NotFound() {
        when(fermeRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            fermeService.getFermeById(1);
        });

        assertEquals("Ferme not found", exception.getMessage());
    }

    @Test
    public void testUpdateFerme() {
        when(fermeRepository.findById(1)).thenReturn(Optional.of(ferme));
        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);
        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(fermeDto);

        FermeDto updatedFerme = fermeService.updateFerme(1, fermeDto);

        assertNotNull(updatedFerme);
        assertEquals("Ferme Test", updatedFerme.getNom());
    }

    @Test
    public void testDeleteFerme() {
        when(fermeRepository.findById(org.mockito.ArgumentMatchers.anyInt())).thenReturn(Optional.of(ferme));

        fermeService.deleteFerme(1);

        verify(fermeRepository, times(1)).delete(org.mockito.ArgumentMatchers.any(Ferme.class));
    }
    @Test
    public void testDeleteFerme_NotFound() {
        when(fermeRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            fermeService.deleteFerme(1);
        });

        assertEquals("Ferme with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testFindByCriteria_EmptyCriteria() {
        FermeSearchCriteria criteria = new FermeSearchCriteria();
        List<FermeDto> fermeDtos = List.of(fermeDto);
        when(fermeRepository.findByCriteria(criteria)).thenReturn(fermeDtos);

        List<FermeDto> result = fermeService.findByCriteria(criteria);


        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    @Test
    public void testAddFerme_InvalidDateCreation() {
        FermeDto invalidFermeDto = new FermeDto();
        invalidFermeDto.setDateCreation(LocalDate.now().plusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fermeService.addFerme(invalidFermeDto);
        });

        assertEquals("The creation date cannot be in the future.", exception.getMessage());
    }
    @Test
    public void testAddFerme_InvalidSuperficie() {
        FermeDto invalidFermeDto = new FermeDto();
        invalidFermeDto.setNom("Invalid Superficie Farm");
        invalidFermeDto.setLocalisation("Invalid Superficie Location");
        invalidFermeDto.setSuperficie(-100);
        invalidFermeDto.setDateCreation(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> {
            fermeService.addFerme(invalidFermeDto);
        });
    }

    @Test
    public void testUpdateFerme_PartialUpdate() {

        when(fermeRepository.findById(1)).thenReturn(Optional.of(ferme));

        FermeDto partialUpdate = new FermeDto();
        partialUpdate.setNom("Updated Farm Name");
        partialUpdate.setSuperficie(200000);

        Ferme updatedFermeEntity = new Ferme();
        updatedFermeEntity.setId(1);
        updatedFermeEntity.setNom("Updated Farm Name");
        updatedFermeEntity.setSuperficie(200000);

        when(fermeRepository.save(any(Ferme.class))).thenReturn(updatedFermeEntity);
        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(partialUpdate);


        FermeDto updatedFerme = fermeService.updateFerme(1, partialUpdate);


        assertNotNull(updatedFerme);
        assertEquals("Updated Farm Name", updatedFerme.getNom());
        assertEquals(200000, updatedFerme.getSuperficie());


        verify(fermeRepository, times(1)).findById(1);
        verify(fermeRepository, times(1)).save(any(Ferme.class));
    }


    @Test
    public void testFindByCriteria_ValidCriteria() {
        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setNom("Test Farm");

        when(fermeRepository.findByCriteria(criteria)).thenReturn(List.of(fermeDto));

        List<FermeDto> fermes = fermeService.findByCriteria(criteria);

        assertNotNull(fermes);
        assertEquals(1, fermes.size());
        assertEquals("Ferme Test", fermes.get(0).getNom());
    }





}
