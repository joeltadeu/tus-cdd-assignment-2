package com.pms.patient.repository;

import com.pms.patient.controller.dto.PatientFilter;
import com.pms.patient.model.Patient;
import org.springframework.data.domain.Page;

public interface PatientRepositoryCustom {
    Page<Patient> findAllWithFilters(PatientFilter filter);
}
