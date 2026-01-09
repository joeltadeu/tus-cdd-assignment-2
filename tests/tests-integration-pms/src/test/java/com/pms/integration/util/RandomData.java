package com.pms.integration.util;

import com.github.javafaker.Faker;

public class RandomData {
  private static final Faker faker = new Faker();

  public static String randomFirstName() {
    return faker.name().firstName();
  }

  public static String randomLastName() {
    return faker.name().lastName();
  }

  public static String randomEmail(String first, String last) {
    System.out.println((first + "." + last + "@example.com").toLowerCase());
    return (first + "." + last + "@example.com").toLowerCase();
  }
}
