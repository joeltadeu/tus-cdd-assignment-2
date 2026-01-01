package com.pms.performance.payload;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DoctorPayloadBuilder {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    private static final String TITLE = "Dr.";
    private static final List<String> DEPARTMENTS = Arrays.asList(
            "Primary Care", "Cardiology", "Dermatology", "Pediatrics", "Oncology"
    );

    public static String build() {
        String firstName = randomFirstName();
        String lastName = randomLastName();
        String email = buildEmail(firstName, lastName);
        String phone = randomPhone();
        int specialityId = randomSpecialityId();
        String department = randomDepartment();

        return String.format(
                "{ \"firstName\": \"%s\", " +
                        "\"lastName\": \"%s\", " +
                        "\"title\": \"%s\", " +
                        "\"specialityId\": %d, " +
                        "\"email\": \"%s\", " +
                        "\"phone\": \"%s\", " +
                        "\"department\": \"%s\" }",
                firstName, lastName, TITLE, specialityId, email, phone, department
        );
    }

    private static String randomFirstName() {
        return faker.name().firstName();
    }

    private static String randomLastName() {
        return faker.name().lastName();
    }

    private static String buildEmail(String firstName, String lastName) {
        return (firstName + "." + lastName + "@example.com").toLowerCase();
    }

    private static String randomPhone() {
        return String.format("+1-%03d-%04d",
                100 + random.nextInt(900),
                1000 + random.nextInt(9000));
    }

    private static int randomSpecialityId() {
        return 1 + random.nextInt(11); // 1–11
    }

    private static String randomDepartment() {
        return DEPARTMENTS.get(random.nextInt(DEPARTMENTS.size()));
    }
}
