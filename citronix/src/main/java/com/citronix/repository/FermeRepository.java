package com.citronix.repository;

import com.citronix.dto.FermeSearchCriteria;
import com.citronix.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FermeRepository extends JpaRepository<Ferme, Integer> {
    Optional<Ferme> findById(int id);




}
