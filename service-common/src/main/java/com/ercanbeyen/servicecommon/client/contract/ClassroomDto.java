package com.ercanbeyen.servicecommon.client.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClassroomDto(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotNull(message = "Capacity is mandatory")
        Integer capacity) {

}
