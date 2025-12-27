package com.pms.appointment.repository;

import com.pms.models.dto.appointment.AppointmentFilter;
import com.pms.appointment.model.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.*;

public class AppointmentRepositoryImpl implements AppointmentRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public AppointmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Page<Appointment> findAllWithFilters(AppointmentFilter filter) {
        CriteriaQuery<Appointment> query = criteriaBuilder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        // Get predicate for the main query
        Predicate predicate = getPredicate(filter, root);
        if (predicate != null) {
            query.where(predicate);
        }

        Pageable pageable = getPageable(filter);

        // Add ordering if needed
        query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        // Get the result list
        List<Appointment> result = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Get total count using the filter criteria
        long total = getRecordsCount(filter);

        return new PageImpl<>(result, pageable, total);
    }

    private Predicate getPredicate(AppointmentFilter criteria, Root<Appointment> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(criteria.getPatientId())) {
            predicates.add(
                    criteriaBuilder.equal(root.get("patientId"), criteria.getPatientId())
            );
        }

        if (Objects.nonNull(criteria.getDoctorId())) {
            predicates.add(
                    criteriaBuilder.equal(root.get("doctorId"), criteria.getDoctorId())
            );
        }

        if (Objects.nonNull(criteria.getStatus())) {
            predicates.add(
                    criteriaBuilder.equal(root.get("status"), criteria.getStatus())
            );
        }

        return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(AppointmentFilter page, CriteriaQuery<Appointment> criteriaQuery, Root<Appointment> patientRoot) {
        if (page.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(patientRoot.get(page.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(patientRoot.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(AppointmentFilter page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getRecordsCount(AppointmentFilter criteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Appointment> from = countQuery.from(Appointment.class);

        // Create a new predicate specifically for this count query
        Predicate countPredicate = getPredicate(criteria, from);

        countQuery.select(criteriaBuilder.count(from));
        if (countPredicate != null) {
            countQuery.where(countPredicate);
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
