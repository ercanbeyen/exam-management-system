package com.ercanbeyen.schoolservice.entity;

import com.ercanbeyen.schoolservice.embeddable.Classroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private String name;
    private String location;
    private String owner;
    private List<Classroom> classrooms;
}
