package com.vekrest.vekproducer.vekproducer.controller.dto.request;

import com.vekrest.vekproducer.vekproducer.entities.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
public record ClientRequest(
        @NotBlank(message = "O nome e obrigatorio")
        String name,

        @NotNull(message = "A data de nascimento e obrigatoria")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birth,

        @NotNull(message = "O endereco e obrigatorio")
        @Valid
        Address address
) {
}
