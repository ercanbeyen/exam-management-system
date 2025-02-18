package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {
    Optional<Exam> findBySubject(String subject);
}
