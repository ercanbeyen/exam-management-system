package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.ExamResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, String> {
    Page<ExamResult> findAllByCandidateId(String candidateId, Pageable pageable);
}
