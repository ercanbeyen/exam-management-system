package com.ercanbeyen.servicecommon.client.contract;

import com.ercanbeyen.servicecommon.client.contract.embeddable.Classroom;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record SchoolDto(
        String id,
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Location is mandatory")
        String location,
        @NotBlank(message = "Owner is mandatory")
        String owner,
        Set<@Valid Classroom> classrooms) {

}
