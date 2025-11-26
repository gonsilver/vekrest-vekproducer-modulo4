package com.vekrest.vekproducer.vekproducer.integration.dto.request;

public record VekSecurityLoginRequest(
        String username,
        String password
) {}