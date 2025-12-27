package com.pms.patient.controller.mapper;

import com.pms.patient.controller.dto.PatientRequest;
import com.pms.patient.controller.dto.PatientResponse;
import com.pms.patient.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(target = "id", ignore = true)
    Patient toPatient(PatientRequest patientRequest);
    PatientResponse toPatientResponse(Patient patient);
}
