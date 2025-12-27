package com.pms.doctor.repository;

import com.pms.doctor.model.Doctor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, DoctorRepositoryCustom {
    boolean existsByEmail(@NotNull @Email String email);
}
