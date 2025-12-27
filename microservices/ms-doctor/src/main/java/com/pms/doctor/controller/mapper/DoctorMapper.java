package com.pms.doctor.controller.mapper;

import com.pms.doctor.model.Doctor;
import com.pms.doctor.model.Speciality;
import com.pms.models.dto.doctor.DoctorRequest;
import com.pms.models.dto.doctor.DoctorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speciality", source = "specialityId")
    Doctor toDoctor(DoctorRequest doctorRequest);

    @Mapping(target = "speciality", source = "speciality.description")
    DoctorResponse toDoctorResponse(Doctor doctor);

    default Speciality map(Long specialityId) {
        if (specialityId == null) {
            return null;
        }
        Speciality speciality = new Speciality();
        speciality.setId(specialityId);
        return speciality;
    }
}
