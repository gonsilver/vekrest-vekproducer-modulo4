package com.vekrest.vekproducer.vekproducer.integration.adapter;

import com.vekrest.vekproducer.vekproducer.entities.Token;
import com.vekrest.vekproducer.vekproducer.integration.dto.response.VekSecurityLoginResponse;

public class VekSecurityIntegrationAdapter {
    private VekSecurityIntegrationAdapter() {
    }

    public static Token cast(VekSecurityLoginResponse response) {
        return new Token(
                response.token()
        );
    }
}
