package com.citronix.repository;

import com.citronix.entity.Detail_recolte;
import com.citronix.entity.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRecolteRepository extends JpaRepository<Detail_recolte, Integer> {
    List<Detail_recolte> findByRecolteId(int recolteId);
    List<Detail_recolte> findByArbreIdAndRecolteSaison(int arbreId, Saison saison);
    List<Detail_recolte> findByArbreId(int id);
}
