package com.ercanbeyen.studentservice.repository;

import com.ercanbeyen.studentservice.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

}
