package com.pms.patient.service;

import com.pms.exception.BadRequestException;
import com.pms.exception.NotFoundException;
import com.pms.patient.controller.dto.PatientFilter;
import com.pms.patient.model.Patient;
import com.pms.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    public Patient findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Patient with Id %s was not found".formatted(id)));
    }

    public Page<Patient> findAll(PatientFilter filter) {
        return repository.findAllWithFilters(filter);
    }

    public void insert(Patient patient) {
        log.info("Before save, checking if there is another patient saved in the database with the same email [{}]",
                patient.getEmail());
        if (repository.existsByEmail(patient.getEmail())) {
            throw new BadRequestException("There is another patient using the same email '%s' informed".formatted(patient.getEmail()));
        }

        patient.setCreatedAt(LocalDateTime.now());
        repository.save(patient);
    }

    public void update(Long id, Patient patient) {
        log.info("Before update, checking if the patient exists...");
        var savedPatient = findById(id);
        savedPatient.setEmail(patient.getEmail());
        savedPatient.setFirstName(patient.getFirstName());
        savedPatient.setLastName(patient.getLastName());
        savedPatient.setAddress(patient.getAddress());
        savedPatient.setDateOfBirth(patient.getDateOfBirth());
        repository.save(savedPatient);
    }

    public void delete(Long id) {
        log.info("Before delete, checking if the patient exists...");
        final var patient = findById(id);

        log.info("Patient found, deleting...");
        repository.delete(patient);
    }
}
