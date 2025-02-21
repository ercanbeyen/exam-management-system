package com.ercanbeyen.servicecommon.client.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SchoolDto(
        String id,
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Location is mandatory")
        String location,
        @NotBlank(message = "Owner is mandatory")
        String owner,
        List<@Valid ClassroomDto> classroomDtos) {

}
