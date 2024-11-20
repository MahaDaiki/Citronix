package com.citronix.repository.Implementation;

import com.citronix.dto.FermeDto;
import com.citronix.dto.FermeSearchCriteria;
import com.citronix.entity.Ferme;
import com.citronix.repository.CustomFermeRepository;
import com.citronix.repository.FermeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FermeRepositoryImpl  implements CustomFermeRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<FermeDto> findByCriteria(FermeSearchCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ferme> criteriaQuery = builder.createQuery(Ferme.class);
        Root<Ferme> root = criteriaQuery.from(Ferme.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getNom() != null && !criteria.getNom().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("nom")), "%" + criteria.getNom().toLowerCase() + "%"));
        }
        if (criteria.getLocalisation() != null && !criteria.getLocalisation().isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("localisation")), "%" + criteria.getLocalisation().toLowerCase() + "%"));
        }
        if (criteria.getSuperficie() > 0) {
            predicates.add(builder.equal(root.get("superficie"), criteria.getSuperficie()));
        }

        if (criteria.getDateCreation() != null) {
            predicates.add(builder.equal(root.get("dateCreation"), criteria.getDateCreation()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        List<Ferme> fermeList = entityManager.createQuery(criteriaQuery).getResultList();
        List<FermeDto> fermeDtoList = new ArrayList<>();
        for (Ferme ferme : fermeList) {
            fermeDtoList.add(new FermeDto().toDTO(ferme));
        }


        return fermeDtoList;
    }
}
