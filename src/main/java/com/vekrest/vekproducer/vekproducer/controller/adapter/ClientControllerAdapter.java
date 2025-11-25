package com.vekrest.vekproducer.vekproducer.controller.adapter;

import com.vekrest.vekproducer.vekproducer.controller.dto.request.ClientRequest;
import com.vekrest.vekproducer.vekproducer.entities.Address;
import com.vekrest.vekproducer.vekproducer.entities.Client;

public class ClientControllerAdapter {
    private ClientControllerAdapter() {
    }

    public static Client cast(ClientRequest request) {
        return new Client(
                request.name(),
                request.birth(),
                new Address(
                    request.address().cep(),
                    request.address().state()
                )
        );
    }
}
