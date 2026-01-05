package com.pms.doctor.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.Schema;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Doctor Service API",
            version = "1.0.0",
            description =
                "The Doctor Service API is responsible for managing the lifecycle of doctor profiles within the system. It provides endpoints to create, retrieve, update, and delete medical staff records, including essential details such as specialties, departments, and contact information.",
            contact = @Contact(name = "Joel Silva", email = "joeltadeu@gmail.com"),
            license =
                @License(name = "MIT License", url = "https://mit-license.org/")),
    servers = {
      @Server(url = "http://localhost:9082", description = "Development Server"),
      @Server(url = "http://pms.local", description = "Kubernetes PMS Cluster")
    })
@Configuration
public class OpenApiConfig {

  /** Default constructor for OpenApiConfig. */
  public OpenApiConfig() {}

  static {
    SpringDocUtils.getConfig()
        .replaceWithSchema(
            LocalDate.class,
            new Schema<LocalDate>()
                .type("string")
                .format("date")
                .example(LocalDate.now().format(DateTimeFormatter.ISO_DATE)));
  }
}
