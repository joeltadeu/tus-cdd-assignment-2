package com.pms.doctor.service;

import com.pms.doctor.repository.SpecialityRepository;
import com.pms.exception.BadRequestException;
import com.pms.exception.NotFoundException;
import com.pms.doctor.model.Doctor;
import com.pms.doctor.repository.DoctorRepository;
import com.pms.models.dto.doctor.DoctorFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;

    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor with Id %s was not found".formatted(id)));
    }

    public Page<Doctor> findAll(DoctorFilter filter) {
        return doctorRepository.findAllWithFilters(filter);
    }

    public void insert(Doctor doctor) {
        log.info("Before save, checking if there is another doctor saved in the database with the same email [{}]",
                doctor.getEmail());
        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new BadRequestException("There is another doctor using the same email '%s' informed".formatted(doctor.getEmail()));
        }

        var foundSpeciality = specialityRepository.findById(doctor.getSpeciality().getId())
                .orElseThrow(() -> new BadRequestException("Speciality not found with id: " + doctor.getSpeciality().getId()));
        doctor.setSpeciality(foundSpeciality);

        doctor.setCreatedAt(LocalDateTime.now());
        doctorRepository.save(doctor);
    }

    public void update(Long id, Doctor doctor) {
        log.info("Before update, checking if the doctor exists...");
        var savedDoctor = findById(id);
        savedDoctor.setEmail(doctor.getEmail());
        savedDoctor.setFirstName(doctor.getFirstName());
        savedDoctor.setLastName(doctor.getLastName());
        savedDoctor.setPhone(doctor.getPhone());
        savedDoctor.setDepartment(doctor.getDepartment());
        savedDoctor.setSpeciality(doctor.getSpeciality());
        savedDoctor.setTitle(doctor.getTitle());

        doctorRepository.save(savedDoctor);
    }

    public void delete(Long id) {
        log.info("Before delete, checking if the doctor exists...");
        final var doctor = findById(id);

        log.info("Doctor found, deleting...");
        doctorRepository.delete(doctor);
    }
}
