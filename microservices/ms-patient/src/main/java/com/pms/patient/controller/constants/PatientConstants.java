package com.pms.patient.controller.constants;

public class PatientConstants {
    public static final String PATIENT_EXAMPLE_ERROR_404_NOT_FOUND = """
            {
                "code": 404,
                "status": "Not Found",
                "description": "Patient with id 10 was not found",
                "date": "2022-10-28T21:39:54.603263862"
            }
            """;


    public static final String EXAMPLE_ERROR_500_INTERNAL_SERVER_ERROR = """
            {
                "code": 500,
                "status": "Internal Server Error",
                "description": "An error occurred while processing your request",
                "date": "2022-10-28T21:39:54.603263862"
            }
            """;

    public static final String PATIENT_EXAMPLE_ERROR_400_BAD_REQUEST = """
            {
                 "code": 400,
                 "status": "Bad Request",
                 "description": "Validation Exception",
                 "date": "2022-11-25T20:14:04.078313400",
                 "attributes": [
                     {
                         "attribute": "name",
                         "errors": [
                             "Patient's name cannot be null"
                         ]
                     },
                     {
                         "attribute": "email",
                         "errors": [
                             "Patient's email cannot be null"
                         ]
                     }
                 ]
            }
            """;
}
