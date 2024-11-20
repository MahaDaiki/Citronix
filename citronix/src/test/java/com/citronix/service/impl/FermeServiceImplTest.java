package com.citronix.service.impl;

import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import com.citronix.entity.Ferme;
import com.citronix.exception.ResourceNotFoundException;
import com.citronix.repository.FermeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FermeServiceImplTest {

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private FermeServiceImpl fermeService;


    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Ferme> criteriaQuery;

    @Mock
    private Root<Ferme> root;

    @Mock
    private TypedQuery<Ferme> typedQuery;

    private Ferme ferme;

    @BeforeEach
    public void setUp() {


        ferme = new Ferme();
        ferme.setId(1);
        ferme.setNom("Ferme Test");
        ferme.setLocalisation("Localisation Test");
        ferme.setSuperficie(100);
        ferme.setDateCreation(LocalDate.now());
    }

    @Test
    public void testAddFerme() {

        FermeDto fermeDto = new FermeDto();
        fermeDto.setNom("Ferme Test");
        fermeDto.setLocalisation("Localisation Test");
        fermeDto.setSuperficie(100);

        when(fermeRepository.save(org.mockito.ArgumentMatchers.any(Ferme.class))).thenReturn(ferme);


        FermeDto savedFerme = fermeService.addFerme(fermeDto);


        verify(fermeRepository, times(1)).save(org.mockito.ArgumentMatchers.any(Ferme.class));
        assert savedFerme != null;
        assert savedFerme.getNom().equals("Ferme Test");
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
        FermeDto fermeDto = new FermeDto();
        fermeDto.setNom("Updated Ferme");
        when(fermeRepository.findById(org.mockito.ArgumentMatchers.anyInt())).thenReturn(Optional.of(ferme));
        when(fermeRepository.save(org.mockito.ArgumentMatchers.any(Ferme.class))).thenReturn(ferme);

        FermeDto updatedFerme = fermeService.updateFerme(1, fermeDto);

        verify(fermeRepository, times(1)).save(org.mockito.ArgumentMatchers.any(Ferme.class));
        assert updatedFerme.getNom().equals("Updated Ferme");
    }

    @Test
    public void testDeleteFerme() {
        // Arrange
        when(fermeRepository.findById(org.mockito.ArgumentMatchers.anyInt())).thenReturn(Optional.of(ferme));

        // Act
        fermeService.deleteFerme(1);

        // Assert
        verify(fermeRepository, times(1)).delete(org.mockito.ArgumentMatchers.any(Ferme.class));
    }
    @Test
    public void testFindByCriteria_Nom() {
        Ferme ferme = new Ferme();
        ferme.setNom("Ferme 1");
        ferme.setLocalisation("Location 1");
        ferme.setSuperficie(100);
        ferme.setDateCreation(LocalDate.now());

        List<Ferme> fermeList = Arrays.asList(ferme);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setNom("Ferme 1");

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ferme 1", result.get(0).getNom());
    }

    @Test
    public void testFindByCriteria_Localisation() {
        Ferme ferme = new Ferme();
        ferme.setNom("Ferme 1");
        ferme.setLocalisation("Location 1");
        ferme.setSuperficie(100);
        ferme.setDateCreation(LocalDate.now());

        List<Ferme> fermeList = Arrays.asList(ferme);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setLocalisation("Location 1");

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Location 1", result.get(0).getLocalisation());
    }
    @Test
    public void testFindByCriteria_Superficie() {
        Ferme ferme = new Ferme();
        ferme.setNom("Ferme 1");
        ferme.setLocalisation("Location 1");
        ferme.setSuperficie(100);
        ferme.setDateCreation(LocalDate.now());

        List<Ferme> fermeList = Arrays.asList(ferme);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setSuperficie(100);

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getSuperficie());
    }

    @Test
    public void testFindByCriteria_DateCreation() {
        LocalDate date = LocalDate.of(2023, 1, 1);

        Ferme ferme = new Ferme();
        ferme.setNom("Ferme 1");
        ferme.setLocalisation("Location 1");
        ferme.setSuperficie(100);
        ferme.setDateCreation(date);

        List<Ferme> fermeList = Arrays.asList(ferme);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setDateCreation(date);

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(date, result.get(0).getDateCreation());
    }

    @Test
    public void testFindByCriteria_MultipleCriteria() {
        Ferme ferme = new Ferme();
        ferme.setNom("Ferme 1");
        ferme.setLocalisation("Location 1");
        ferme.setSuperficie(100);
        ferme.setDateCreation(LocalDate.now());

        List<Ferme> fermeList = Arrays.asList(ferme);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setNom("Ferme 1");
        criteria.setLocalisation("Location 1");

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ferme 1", result.get(0).getNom());
        assertEquals("Location 1", result.get(0).getLocalisation());
    }

    @Test
    public void testFindByCriteria_NoResults() {
        List<Ferme> fermeList = new ArrayList<>();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setNom("Nonexistent Farm");

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByCriteria_CaseInsensitive() {
        Ferme ferme = new Ferme();
        ferme.setNom("FERME 1");
        ferme.setLocalisation("Location 1");
        ferme.setSuperficie(100);
        ferme.setDateCreation(LocalDate.now());

        List<Ferme> fermeList = Arrays.asList(ferme);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Ferme.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Ferme.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(fermeList);

        FermeSearchCriteria criteria = new FermeSearchCriteria();
        criteria.setNom("ferme 1");

        List<FermeDto> result = fermeService.findByCriteria(criteria);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("FERME 1", result.get(0).getNom());
    }







}
