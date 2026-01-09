package com.pms.performance.config;

import com.pms.performance.simulation.PmsLoadSimulation;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.Getter;

public class SimulationConfig {

  private static final Properties FILE_PROPS = loadProperties();

  @Getter private static final String patientUrl = get("patientUrl", "http://localhost:9081");

  @Getter private static final String doctorUrl = get("doctorUrl", "http://localhost:9082");

  @Getter
  private static final String appointmentUrl = get("appointmentUrl", "http://localhost:9083");

  @Getter private static final int users = Integer.parseInt(get("users", "100"));

  @Getter private static final int durationMinutes = Integer.parseInt(get("durationMinutes", "5"));

  private static String get(String key, String defaultValue) {
    String sysValue = System.getProperty(key);
    if (sysValue != null && !sysValue.isBlank()) {
      return sysValue;
    }

    String fileValue = FILE_PROPS.getProperty(key);
    if (fileValue != null && !fileValue.isBlank()) {
      return fileValue;
    }

    return defaultValue;
  }

  private static Properties loadProperties() {
    Properties props = new Properties();
    try (InputStream input =
        PmsLoadSimulation.class.getClassLoader().getResourceAsStream("application.properties")) {

      if (input == null) {
        throw new RuntimeException("application.properties not found in resources folder!");
      }
      props.load(input);

    } catch (IOException e) {
      throw new RuntimeException("Failed to load application.properties", e);
    }
    return props;
  }
}
