package com.pms.doctor.repository;

import com.pms.doctor.model.Doctor;
import com.pms.models.dto.doctor.DoctorFilter;
import org.springframework.data.domain.Page;

public interface DoctorRepositoryCustom {
    Page<Doctor> findAllWithFilters(DoctorFilter filter);
}
