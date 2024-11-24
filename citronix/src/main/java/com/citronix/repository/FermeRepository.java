package com.citronix.repository;


import com.citronix.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface FermeRepository extends JpaRepository<Ferme, Integer>,CustomFermeRepository {
    Optional<Ferme> findById(int id);





}
