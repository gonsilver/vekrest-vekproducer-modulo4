package com.vekrest.vekproducer.vekproducer.integration.interfaces;

import com.vekrest.vekproducer.vekproducer.entities.Token;

public interface VekSecurityIntegration {
    Token getToken(String username, String password);
}
