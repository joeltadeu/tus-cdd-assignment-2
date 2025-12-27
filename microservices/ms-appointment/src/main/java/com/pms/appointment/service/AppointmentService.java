package com.pms.appointment.service;

import com.pms.appointment.client.DoctorClient;
import com.pms.appointment.client.PatientClient;
import com.pms.appointment.controller.mapper.AppointmentMapper;
import com.pms.appointment.model.Appointment;
import com.pms.appointment.repository.AppointmentRepository;
import com.pms.exception.NotFoundException;
import com.pms.models.dto.appointment.AppointmentFilter;
import com.pms.models.dto.appointment.CancelAppointment;
import com.pms.models.dto.appointment.AppointmentStatus;
import com.pms.models.dto.appointment.AppointmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final DoctorClient doctorClient;
    private final PatientClient patientClient;

    public AppointmentResponse findByIdEnriched(Long id) {
        var appointment = findById(id);
        return createAppointmentResponse(appointment);
    }

    public Appointment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment with Id %s was not found".formatted(id)));
    }

    public Page<Appointment> findAll(AppointmentFilter filter) {
        return repository.findAllWithFilters(filter);
    }

    public AppointmentResponse insert(Appointment appointment) {
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setEndTime(appointment.getStartTime().plusHours(1));
        appointment.setDuration(60);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        repository.save(appointment);

        return createAppointmentResponse(appointment);
    }

    public AppointmentResponse paid(Long id) {
        var appointment = findById(id);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setLastUpdated(LocalDateTime.now());
        repository.save(appointment);

        return createAppointmentResponse(appointment);
    }

    private AppointmentResponse createAppointmentResponse(Appointment appointment) {
        var doctor = doctorClient.findById(appointment.getDoctorId());
        var patient = patientClient.findById(appointment.getPatientId());

        return mapper.toCreateAppointmentResponse(doctor, patient, appointment);
    }

    public void cancel(Long id, CancelAppointment cancelAppointment) {
        log.info("Before update, checking if the appointment exists...");
        var savedPatient = findById(id);
        savedPatient.setCancellationReason(cancelAppointment.getReason());
        savedPatient.setCancellationTime(LocalDateTime.now());
        savedPatient.setLastUpdated(LocalDateTime.now());
        repository.save(savedPatient);
    }
}
