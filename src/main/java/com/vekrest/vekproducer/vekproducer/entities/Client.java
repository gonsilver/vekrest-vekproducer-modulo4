package com.vekrest.vekproducer.vekproducer.entities;

public record Client(
        String name,
        String birth,
        Address address
) {}