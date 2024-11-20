package com.citronix.service.impl;

import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import com.citronix.entity.Ferme;
import com.citronix.exception.ResourceNotFoundException;
import com.citronix.repository.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FermeServiceImplTest {

    @Mock
    private FermeRepository fermeRepository;

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

        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);

        FermeDto savedFerme = fermeService.addFerme(fermeDto);

        assertNotNull(savedFerme);
        assertEquals("Ferme Test", savedFerme.getNom());
        assertEquals("Localisation Test", savedFerme.getLocalisation());
    }


    @Test
    public void testGetAllFermes() {
        List<Ferme> fermeList = new ArrayList<>();

        fermeList.add(ferme);
        when(fermeRepository.findAll()).thenReturn(fermeList);
        List<FermeDto> fermes = fermeService.getAllFermes();


        assert fermes.size() == 1;
        assert fermes.get(0).getNom().equals("Ferme Test");
    }

    @Test
    public void testGetFermeById_Success() {
        when(fermeRepository.findById(org.mockito.ArgumentMatchers.anyInt())).thenReturn(Optional.of(ferme));

        FermeDto fermeDto = fermeService.getFermeById(1);

        assert fermeDto.getNom().equals("Ferme Test");
    }

    @Test
    public void testGetFermeById_NotFound() {
        when(fermeRepository.findById(org.mockito.ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        try {
            fermeService.getFermeById(2);
        } catch (ResourceNotFoundException e) {
            assert e.getMessage().equals("Ferme not found");
        }
    }

    @Test
    public void testUpdateFerme() {
        when(fermeRepository.findById(1)).thenReturn(Optional.of(ferme));
        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);

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
    public void testFindByCriteria() {
        FermeSearchCriteria criteria = new FermeSearchCriteria();
        when(fermeRepository.findByCriteria(criteria)).thenReturn(List.of(new FermeDto().toDTO(ferme)));

        var result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    @Test
    public void testAddFerme_InvalidDateCreation() {
        FermeDto invalidFermeDto = new FermeDto();
        invalidFermeDto.setNom("Invalid Farm");
        invalidFermeDto.setLocalisation("Invalid Location");
        invalidFermeDto.setSuperficie(100);
        invalidFermeDto.setDateCreation(LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            fermeService.addFerme(invalidFermeDto);
        });
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

        partialUpdate.setSuperficie(100000);

        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);


        FermeDto updatedFerme = fermeService.updateFerme(1, partialUpdate);


        assertNotNull(updatedFerme);
        assertEquals("Updated Farm Name", updatedFerme.getNom());
        assertEquals("Localisation Test", updatedFerme.getLocalisation());
        assertEquals(100000, updatedFerme.getSuperficie());
    }


    @Test
    public void testDeleteFerme_FermeNotFound() {
        when(fermeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            fermeService.deleteFerme(999);
        });
    }

    @Test
    public void testFindByCriteria_EmptyCriteria() {
        FermeSearchCriteria emptyCriteria = new FermeSearchCriteria();

        when(fermeRepository.findByCriteria(emptyCriteria)).thenReturn(List.of(new FermeDto().toDTO(ferme)));

        List<FermeDto> fermes = fermeService.findByCriteria(emptyCriteria);

        assertNotNull(fermes);
        assertFalse(fermes.isEmpty());
    }
    @Test
    public void testFindByCriteria_ValidCriteria() {
        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setNom("Test Farm");

        when(fermeRepository.findByCriteria(criteria)).thenReturn(List.of(new FermeDto().toDTO(ferme)));

        List<FermeDto> fermes = fermeService.findByCriteria(criteria);

        assertNotNull(fermes);
        assertEquals(1, fermes.size());
        assertEquals("Ferme Test", fermes.get(0).getNom());
    }





}
