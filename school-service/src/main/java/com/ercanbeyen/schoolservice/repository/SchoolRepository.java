package com.ercanbeyen.schoolservice.repository;

import com.ercanbeyen.schoolservice.entity.School;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends MongoRepository<School, String> {
    Optional<School> findByName(String name);
    boolean existsByName(String name);
}
