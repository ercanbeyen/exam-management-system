package com.ercanbeyen.schoolservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    private String owner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private List<Classroom> classrooms;
}
