package com.pms.performance.payload;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PatientPayloadBuilder {
  private static final Faker faker = new Faker();
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static String build() {
    String firstName = randomFirstName();
    String lastName = randomLastName();
    String email = buildEmail(firstName, lastName);
    String dateOfBirth = randomDateOfBirth();
    String address = randomAddress();

    return String.format(
        "{ \"firstName\": \"%s\", "
            + "\"lastName\": \"%s\", "
            + "\"email\": \"%s\", "
            + "\"address\": \"%s\", "
            + "\"dateOfBirth\": \"%s\" }",
        firstName, lastName, email, address, dateOfBirth);
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

  private static String randomDateOfBirth() {
    Date birthday = faker.date().birthday(0, 90);

    LocalDate localDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    return localDate.format(DATE_FORMATTER);
  }

  private static String randomAddress() {
    return faker.address().fullAddress();
  }
}
