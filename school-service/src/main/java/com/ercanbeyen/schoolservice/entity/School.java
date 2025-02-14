package com.ercanbeyen.schoolservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schools")
public class School {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private String location;
    private String owner;
    private List<Classroom> classrooms;
}
