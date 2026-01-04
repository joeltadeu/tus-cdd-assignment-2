package com.pms.patient.config;

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
            title = "Patient Service API",
            version = "1.0.0",
            description =
                "The Patient Service API manages comprehensive patient records, handling personal and demographic information. It provides endpoints to register new patients, retrieve individual profiles, update existing records, and manage patient data securely.",
            contact = @Contact(name = "Joel Silva", email = "joeltadeu@gmail.com"),
            license =
                @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")),
    servers = {
      @Server(url = "http://localhost:9081", description = "Development Server"),
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
