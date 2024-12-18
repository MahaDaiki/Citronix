package com.citronix.service.impl;
import com.citronix.dto.ChampDto;
import com.citronix.entity.Champ;
import com.citronix.entity.Ferme;
import com.citronix.exception.ResourceNotFoundException;
import com.citronix.mapper.ChampMapper;
import com.citronix.repository.ChampRepository;
import com.citronix.repository.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ChampServiceImplTest {
    @Mock
    private ChampRepository champRepository;

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private ChampMapper champMapper;

    @InjectMocks
    private ChampServiceImpl champService;

    private Ferme ferme;
    private Champ champ;
    private ChampDto champDto;

    @BeforeEach
    void setUp() {

        ferme = new Ferme();
        ferme.setId(1);
        champ = new Champ();
        champ.setId(1);
        champ.setNom("Champ1");
        champ.setFerme(ferme);
        champDto = new ChampDto();
        champDto.setNom("Champ1");
        champDto.setSuperficie(100);
        champDto.setFermeId(1);
    }

    @Test
    void addChamp_ShouldReturnAddedChamp_WhenValidData() {
        when(fermeRepository.findById(1)).thenReturn(Optional.of(ferme));
        when(champMapper.toEntity(any(ChampDto.class))).thenReturn(champ);
        when(champRepository.save(any(Champ.class))).thenReturn(champ);
        when(champMapper.toDto(any(Champ.class))).thenReturn(champDto);

        ChampDto result = champService.addChamp(champDto);

        assertNotNull(result);
        assertEquals("Champ1", result.getNom());
        verify(champRepository, times(1)).save(any(Champ.class));


    }


    @Test
    void addChamp_ShouldThrowException_WhenFermeNotFound() {
        when(fermeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> champService.addChamp(champDto));
        assertEquals("Ferme not found", exception.getMessage());
    }

    @Test
    void getAllChamps_ShouldReturnPaginatedListOfChamps() {
        // Arrange
        Champ champ = new Champ();
        champ.setNom("Champ1");
        ChampDto champDto = new ChampDto();
        champDto.setNom("Champ1");


        Page<ChampDto> champPage = new PageImpl<>(Arrays.asList(champDto));


        when(champRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(Arrays.asList(champ)));
        when(champMapper.toDto(any(Champ.class))).thenReturn(champDto);


        Page<ChampDto> champs = champService.getAllChamps(0, 10);


        assertEquals(1, champs.getContent().size());
        assertEquals("Champ1", champs.getContent().get(0).getNom());
        assertTrue(champs.hasContent());
    }

    @Test
    void getChampById_ShouldReturnChamp_WhenFound() {
        when(champRepository.findById(1)).thenReturn(Optional.of(champ));
        when(champMapper.toDto(any(Champ.class))).thenReturn(champDto);

        ChampDto result = champService.getChampById(1);

        assertNotNull(result);
        assertEquals("Champ1", result.getNom());
    }

    @Test
    void getChampById_ShouldThrowException_WhenNotFound() {
        when(champRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> champService.getChampById(1));
        assertEquals("Champ not found", exception.getMessage());
    }

    @Test
    void deleteChamp_ShouldDeleteChamp_WhenFound() {
        when(champRepository.findById(1)).thenReturn(Optional.of(champ));

        champService.deleteChamp(1);

        verify(champRepository, times(1)).delete(champ);
    }

    @Test
    void deleteChamp_ShouldThrowException_WhenNotFound() {
        when(champRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> champService.deleteChamp(1));
        assertEquals("Champ not found", exception.getMessage());
    }

    @Test
    void updateChamp_ShouldReturnUpdatedChamp_WhenValidData() {

        when(champRepository.findById(1)).thenReturn(Optional.of(champ));
        when(champRepository.save(any(Champ.class))).thenReturn(champ);
        when(champMapper.toDto(any(Champ.class))).thenReturn(champDto);
        when(fermeRepository.findById(1)).thenReturn(Optional.of(ferme));

        champDto.setNom("Updated Champ");

        ChampDto result = champService.updateChamp(1, champDto);


        assertNotNull(result);
        assertEquals("Updated Champ", result.getNom());
    }

    @Test
    void updateChamp_ShouldThrowException_WhenChampNotFound() {
        when(champRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> champService.updateChamp(1, champDto));
        assertEquals("Champ with ID 1 not found", exception.getMessage());
    }

    @Test
    void getChampsByFermeId_ShouldReturnChamps_WhenFermeExists() {
        when(fermeRepository.findById(1)).thenReturn(Optional.of(ferme));
        ferme.setChamps(Arrays.asList(champ));
        when(champMapper.toDto(any(Champ.class))).thenReturn(champDto);

        List<ChampDto> champs = champService.getChampsByFermeId(1);

        assertEquals(1, champs.size());
        assertEquals("Champ1", champs.get(0).getNom());
    }

    @Test
    void getChampsByFermeId_ShouldThrowException_WhenFermeNotFound() {
        when(fermeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> champService.getChampsByFermeId(1));
        assertEquals("Ferme not found with ID: 1", exception.getMessage());
    }
}

