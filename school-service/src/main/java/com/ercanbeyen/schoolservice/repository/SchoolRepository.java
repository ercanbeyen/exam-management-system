package com.ercanbeyen.schoolservice.repository;

import com.ercanbeyen.schoolservice.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    Optional<School> findByName(String name);
}
