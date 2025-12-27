package com.pms.controller;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface PmsController {

    String HTTP_STATUS_CODE_CREATED = "201";
    String HTTP_STATUS_CODE_OK = "200";
    String HTTP_STATUS_CODE_UNAUTHORIZED = "401";
    String HTTP_STATUS_CODE_BAD_REQUEST = "400";
    String HTTP_STATUS_CODE_NOT_FOUND = "404";
    String EXAMPLE_BAD_REQUEST_NAME = "Bad request example";
    String EXAMPLE_NOT_FOUND_NAME = "Not found example";
    String EXAMPLE_INTERNAL_SERVER_ERROR_NAME = "Internal server error example";
    String HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR = "500";
    String AUTHORIZATION = "Authorization";

    default URI getURI(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
