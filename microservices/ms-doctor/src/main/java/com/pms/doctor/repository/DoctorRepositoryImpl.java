package com.pms.doctor.repository;

import com.pms.doctor.model.Doctor;
import com.pms.doctor.model.Speciality;
import com.pms.models.dto.doctor.DoctorFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DoctorRepositoryImpl implements DoctorRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public DoctorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Page<Doctor> findAllWithFilters(DoctorFilter filter) {
        CriteriaQuery<Doctor> query = criteriaBuilder.createQuery(Doctor.class);
        Root<Doctor> root = query.from(Doctor.class);

        // Get predicate for the main query
        Predicate predicate = getPredicate(filter, root);
        if (predicate != null) {
            query.where(predicate);
        }

        Pageable pageable = getPageable(filter);

        // Add ordering if needed
        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        // Get the result list
        List<Doctor> result = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Get total count using the filter criteria
        long total = getDoctorsCount(filter);

        return new PageImpl<>(result, pageable, total);
    }

    private Predicate getPredicate(DoctorFilter criteria, Root<Doctor> doctorRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(criteria.getFirstName())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(doctorRoot.get("firstName")),
                            "%" + criteria.getFirstName().toLowerCase(Locale.ROOT) + "%"
                    )
            );
        }

        if (Objects.nonNull(criteria.getLastName())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(doctorRoot.get("lastName")),
                            "%" + criteria.getFirstName().toLowerCase(Locale.ROOT) + "%"
                    )
            );
        }

        if (Objects.nonNull(criteria.getEmail())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(doctorRoot.get("email")),
                            "%" + criteria.getEmail().toLowerCase(Locale.ROOT) + "%"
                    )
            );
        }

        if (Objects.nonNull(criteria.getSpeciality())) {
            // Create a join to the Speciality entity
            Join<Doctor, Speciality> specialityJoin = doctorRoot.join("speciality", JoinType.INNER);

            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(specialityJoin.get("description")),
                            "%" + criteria.getSpeciality().toLowerCase(Locale.ROOT) + "%"
                    )
            );
        }

        return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(DoctorFilter page, CriteriaQuery<Doctor> criteriaQuery, Root<Doctor> doctorRoot) {
        if (page.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(doctorRoot.get(page.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(doctorRoot.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(DoctorFilter page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getDoctorsCount(DoctorFilter criteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Doctor> countRoot = countQuery.from(Doctor.class);

        // Create a new predicate specifically for this count query
        Predicate countPredicate = getPredicate(criteria, countRoot);

        countQuery.select(criteriaBuilder.count(countRoot));
        if (countPredicate != null) {
            countQuery.where(countPredicate);
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
