package com.pms.doctor.controller;

import static com.pms.doctor.controller.constants.DoctorConstants.*;

import com.pms.controller.PmsController;
import com.pms.doctor.controller.mapper.DoctorMapper;
import com.pms.doctor.model.Doctor;
import com.pms.doctor.service.DoctorService;
import com.pms.models.dto.doctor.DoctorFilter;
import com.pms.models.dto.doctor.DoctorRequest;
import com.pms.models.dto.doctor.DoctorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
@AllArgsConstructor
@Slf4j
public class DoctorController implements PmsController {

    private final DoctorService service;
    private final DoctorMapper doctorMapper;

    @Operation(summary = "Register a Doctor",
            description = "This endpoint is responsible to register a new doctor",
            security = @SecurityRequirement(name = AUTHORIZATION))
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_CREATED, description = "Doctor created",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DoctorResponse.class))
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_BAD_REQUEST, description = "Doctor request is invalid",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_BAD_REQUEST_NAME,
                                            description = "A bad request response example when trying to register a doctor",
                                            value = DOCTOR_EXAMPLE_ERROR_400_BAD_REQUEST)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during register the doctor",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to register a doctor",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @PostMapping
    public ResponseEntity<DoctorResponse> insert(
            @RequestBody
            @Valid @NotNull DoctorRequest request) {
        log.info("Request for create a doctor. doctor:{}", request);
        var doctor = doctorMapper.toDoctor(request);
        service.insert(doctor);
        var response = doctorMapper.toDoctorResponse(doctor);

        return ResponseEntity.created(getURI(response.getId())).body(response);
    }

    @Operation(summary = "Update a doctor",
            description = "This endpoint is responsible to update the doctor data by id",
            security = @SecurityRequirement(name = AUTHORIZATION),
            parameters = {@Parameter(name = "id", description = "Id of the doctor to be updated", example = "1", in = ParameterIn.PATH)})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_OK,
                    description = "Doctor updated",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_BAD_REQUEST,
                    description = "Doctor request is invalid",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_BAD_REQUEST_NAME,
                                            description = "A bad request response example when trying to update a doctor",
                                            value = DOCTOR_EXAMPLE_ERROR_400_BAD_REQUEST)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_NOT_FOUND,
                    description = "Doctor not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_NOT_FOUND_NAME,
                                            description = "A not found response example when trying to update a doctor does not exist",
                                            value = DOCTOR_EXAMPLE_ERROR_404_NOT_FOUND)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during update the doctor",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to update a doctor",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable
            Long id,
            @RequestBody
            @Valid @NotNull DoctorRequest request) {
        log.info("Request for update a doctor. doctor:{}", id);
        var doctor = doctorMapper.toDoctor(request);
        service.update(id, doctor);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Retrieve a doctor by id",
            description = "This endpoint is responsible to retrieve the doctor data by id",
            security = @SecurityRequirement(name = AUTHORIZATION),
            parameters = {@Parameter(name = "id", description = "Id of the doctor to be searched", example = "1", in = ParameterIn.PATH)})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_OK, description = "Return doctor",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DoctorResponse.class))
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_NOT_FOUND, description = "Doctor not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_NOT_FOUND_NAME,
                                            description = "A not found response example when trying to retrieve a doctor does not exist",
                                            value = DOCTOR_EXAMPLE_ERROR_404_NOT_FOUND)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during retrieve the doctor",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to retrieve a doctor",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> findById(
            @PathVariable
            Long id) {
        log.info("Request for find doctor by id [{}]", id);

        final var doctor = service.findById(id);
        return ResponseEntity.ok(doctorMapper.toDoctorResponse(doctor));
    }

    @Operation(summary = "Delete the doctor by id",
            description = "This endpoint is responsible to delete the doctor by id",
            security = @SecurityRequirement(name = AUTHORIZATION),
            parameters = {@Parameter(name = "id", description = "Id of the doctor to be deleted", example = "1", in = ParameterIn.PATH)})
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_OK, description = "Doctor deleted",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_NOT_FOUND,
                    description = "Doctor not found",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_NOT_FOUND_NAME,
                                            description = "A not found response example when trying to delete a doctor does not exist",
                                            value = DOCTOR_EXAMPLE_ERROR_404_NOT_FOUND)})
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during delete the doctor",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to delete a doctor",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            Long id) {
        log.info("Request for delete doctor by id [{}]", id);

        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Retrieve a list of doctors",
            security = @SecurityRequirement(name = AUTHORIZATION),
            description = "This endpoint is responsible to retrieve a list of doctors based on the applied filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_STATUS_CODE_OK,
                    description = "Return doctors list"),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_UNAUTHORIZED, description = "Unauthorized",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema())
                    }),
            @ApiResponse(responseCode = HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR,
                    description = "An unexpected error occurred during retrieve the doctors list",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {@ExampleObject(name = EXAMPLE_INTERNAL_SERVER_ERROR_NAME,
                                            description = "A internal server error response example when trying to retrieve a doctors list",
                                            value = EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR)})
                    })
    })
    @GetMapping
    public Page<DoctorResponse> listAll(DoctorFilter filter) {
        log.info("Request for list all doctors");

        Page<Doctor> doctors = service.findAll(filter);
        log.info("Found [{}] results", doctors.getTotalElements());

        var fetchedList = doctors.stream()
                .map(doctorMapper::toDoctorResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(fetchedList, doctors.getPageable(), doctors.getTotalElements());
    }
}
