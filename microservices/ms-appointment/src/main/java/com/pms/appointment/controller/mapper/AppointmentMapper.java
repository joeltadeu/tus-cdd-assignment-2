package com.pms.appointment.controller.mapper;

import com.pms.appointment.model.Appointment;
import com.pms.models.dto.appointment.AppointmentResponse;
import com.pms.models.dto.appointment.CreateAppointmentRequest;
import com.pms.models.dto.doctor.DoctorResponse;
import com.pms.models.dto.patient.PatientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(target = "id", ignore = true)
    Appointment toAppointment(CreateAppointmentRequest createAppointmentRequest);

    AppointmentResponse toCreateAppointmentResponse(Appointment appointment);

    @Mapping(source = "appointment.id", target = "id")
    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "patient", source = "patient")
    AppointmentResponse toCreateAppointmentResponse(DoctorResponse doctor, PatientResponse patient, Appointment appointment);
}
