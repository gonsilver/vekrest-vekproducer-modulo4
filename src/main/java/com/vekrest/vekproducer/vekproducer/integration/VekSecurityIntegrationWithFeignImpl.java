package com.vekrest.vekproducer.vekproducer.integration;

import com.vekrest.vekproducer.vekproducer.entities.Token;
import com.vekrest.vekproducer.vekproducer.integration.client.VekSecurityIntegrationWithFeign;
import com.vekrest.vekproducer.vekproducer.integration.interfaces.VekSecurityIntegration;
import org.springframework.stereotype.Component;

@Component
public class VekSecurityIntegrationWithFeignImpl implements VekSecurityIntegration {
    private final VekSecurityIntegrationWithFeign integration;

    public VekSecurityIntegrationWithFeignImpl(
            VekSecurityIntegrationWithFeign vekSecurityIntegrationWithFeign
    ) {
        this.integration = vekSecurityIntegrationWithFeign;
    }

    @Override
    public Token getToken(String username, String password) {
        var response = integration.login(
                new com.vekrest.vekproducer.vekproducer.integration.dto.request.VekSecurityLoginRequest(
                        username,
                        password
                )
        );

        return new Token(response.token());
    }
}