package com.citronix.repository;

import com.citronix.dto.ArbreDto;
import com.citronix.entity.Arbre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArbreRepository extends JpaRepository<Arbre, Integer> {
    List<Arbre> findByChampId(int champId);
}
