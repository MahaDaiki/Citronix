package com.citronix.repository;


import com.citronix.entity.Recolte;
import com.citronix.entity.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecolteRepository extends JpaRepository<Recolte, Integer> {
    List<Recolte> findBySaison(Saison saison);
}
