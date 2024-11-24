package com.citronix.service.impl;

import com.citronix.dto.VenteDto;
import com.citronix.entity.Recolte;
import com.citronix.entity.Vente;
import com.citronix.mapper.VenteMapper;
import com.citronix.repository.RecolteRepository;
import com.citronix.repository.VenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VenteServiceImplTest {

    @Mock
    private VenteRepository venteRepository;

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private VenteMapper venteMapper;

    @InjectMocks
    private VenteServiceImpl venteService;

    private VenteDto venteDto;
    private Vente vente;
    private Recolte recolte;

    @BeforeEach
    void setUp() {
        recolte = new Recolte();
        recolte.setId(1);
        recolte.setQuantiteTotal(500.0);
        recolte.setVentes(new ArrayList<>());

        venteDto = new VenteDto();
        venteDto.setId(1);
        venteDto.setRecolteId(1);
        venteDto.setQuantite(100.0);
        venteDto.setPrixUnitaire(10.0);
        venteDto.setDate(LocalDate.now());

        vente = new Vente();
        vente.setId(1);
        vente.setRecolte(recolte);
        vente.setQuantite(100.0);
        vente.setPrixUnitaire(10.0);
        vente.setDate(LocalDate.now());
        vente.setRevenu(1000.0);
    }

    @Test
    void addVente_ShouldAddVenteAndReturnDto() {
        when(recolteRepository.findById(anyInt())).thenReturn(Optional.of(recolte));
        when(venteMapper.toEntity(any(VenteDto.class))).thenReturn(vente);
        when(venteRepository.save(any(Vente.class))).thenReturn(vente);
        when(venteMapper.toDto(any(Vente.class))).thenReturn(venteDto);

        VenteDto result = venteService.addVente(venteDto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100.0, result.getQuantite());
        assertEquals(1000.0, vente.getRevenu());
        assertEquals(1, recolte.getVentes().size());
        verify(recolteRepository).save(any(Recolte.class));
    }


    @Test
    void deleteVente_ShouldRemoveVenteFromRecolte() {
        recolte.getVentes().add(vente);
        when(venteRepository.findById(anyInt())).thenReturn(Optional.of(vente));
        doNothing().when(venteRepository).delete(any(Vente.class));

        venteService.deleteVente(1);


        assertTrue(recolte.getVentes().isEmpty());
        verify(venteRepository).delete(vente);
        verify(recolteRepository).save(recolte);
    }

    @Test
    void deleteVente_ShouldThrowExceptionIfVenteNotFound() {

        when(venteRepository.findById(anyInt())).thenReturn(Optional.empty());


        RuntimeException thrown = assertThrows(RuntimeException.class, () -> venteService.deleteVente(1));
        assertEquals("Vente not found", thrown.getMessage());
    }

    @Test
    void getVenteById_ShouldReturnVenteDto() {

        when(venteRepository.findById(anyInt())).thenReturn(Optional.of(vente));
        when(venteMapper.toDto(any(Vente.class))).thenReturn(venteDto);

        VenteDto result = venteService.getVenteById(1);


        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(venteRepository).findById(anyInt());
    }

    @Test
    void getAllVentes_ShouldReturnPaginatedVentes() {
        int pageNum = 0;
        int pageSize = 2;

        Vente vente1 = new Vente();
        vente1.setId(1);
        vente1.setQuantite(10);
        vente1.setPrixUnitaire(50.0);

        Vente vente2 = new Vente();
        vente2.setId(2);
        vente2.setQuantite(5);
        vente2.setPrixUnitaire(60.0);

        List<Vente> ventes = Arrays.asList(vente1, vente2);
        Page<Vente> mockPage = new PageImpl<>(ventes, PageRequest.of(pageNum, pageSize), 10);

        when(venteRepository.findAll(PageRequest.of(pageNum, pageSize))).thenReturn(mockPage);

        VenteDto venteDto1 = new VenteDto();
        venteDto1.setId(1);
        venteDto1.setQuantite(10);
        venteDto1.setPrixUnitaire(50.0);

        VenteDto venteDto2 = new VenteDto();
        venteDto2.setId(2);
        venteDto2.setQuantite(5);
        venteDto2.setPrixUnitaire(60.0);

        when(venteMapper.toDto(vente1)).thenReturn(venteDto1);
        when(venteMapper.toDto(vente2)).thenReturn(venteDto2);

        Page<VenteDto> result = venteService.getAllVentes(pageNum, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(1, result.getContent().get(0).getId());
        assertEquals(2, result.getContent().get(1).getId());


        verify(venteRepository).findAll(PageRequest.of(pageNum, pageSize));
        verify(venteMapper).toDto(vente1);
        verify(venteMapper).toDto(vente2);
    }
}
