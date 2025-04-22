package com.ercanbeyen.servicecommon.client.contract.embeddable;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Classroom {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Capacity is mandatory")
    private Integer capacity;

    public Classroom(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public @NotBlank(message = "Name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is mandatory") String name) {
        this.name = name;
    }

    public @NotNull(message = "Capacity is mandatory") Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity is mandatory") Integer capacity) {
        this.capacity = capacity;
    }
}
