package com.ercanbeyen.schoolservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classroom {
    private String name;
    private Integer capacity;
}
