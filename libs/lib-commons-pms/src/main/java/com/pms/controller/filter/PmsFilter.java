package com.pms.controller.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class PmsFilter {
    @Schema(description = "Zero-base page index (0..N)",
            name = "pageNumber",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "0")
    private Integer pageNumber = 0;

    @Schema(description = "Number of records per page",
            name = "pageSize",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "10")
    private Integer pageSize = 10;

    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";
}