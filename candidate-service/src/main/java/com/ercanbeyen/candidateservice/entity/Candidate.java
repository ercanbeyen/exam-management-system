package com.ercanbeyen.candidateservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "candidates")
public class Candidate {
    @Id
    private String id;
    private String name;
    private int age;
    private String gender;
    private Integer schoolId;
}
