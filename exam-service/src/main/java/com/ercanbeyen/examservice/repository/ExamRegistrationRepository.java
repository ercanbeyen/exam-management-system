package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, String> {
    List<ExamRegistration> findAllByCandidateId(String candidateId);
}
