package com.pms.patient.repository;

import com.pms.patient.controller.dto.PatientFilter;
import com.pms.patient.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class PatientRepositoryImpl implements PatientRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PatientRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Page<Patient> findAllWithFilters(PatientFilter filter) {
        CriteriaQuery<Patient> query = criteriaBuilder.createQuery(Patient.class);
        Root<Patient> root = query.from(Patient.class);

        // Get predicate for the main query
        Predicate predicate = getPredicate(filter, root);
        if (predicate != null) {
            query.where(predicate);
        }

        Pageable pageable = getPageable(filter);

        // Add ordering if needed
        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        // Get the result list
        List<Patient> result = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Get total count using the filter criteria
        long total = getPatientsCount(filter);

        return new PageImpl<>(result, pageable, total);
    }

    private Predicate getPredicate(PatientFilter criteria, Root<Patient> patientRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(criteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(patientRoot.get("name")),
                            "%" + criteria.getName().toLowerCase(Locale.ROOT) + "%"
                    )
            );
        }

        if (Objects.nonNull(criteria.getEmail())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(patientRoot.get("email")),
                            "%" + criteria.getEmail().toLowerCase(Locale.ROOT) + "%"
                    )
            );
        }

        return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PatientFilter page, CriteriaQuery<Patient> criteriaQuery, Root<Patient> patientRoot) {
        if (page.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(patientRoot.get(page.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(patientRoot.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(PatientFilter page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getPatientsCount(PatientFilter criteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Patient> countRoot = countQuery.from(Patient.class);

        // Create a new predicate specifically for this count query
        Predicate countPredicate = getPredicate(criteria, countRoot);

        countQuery.select(criteriaBuilder.count(countRoot));
        if (countPredicate != null) {
            countQuery.where(countPredicate);
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
