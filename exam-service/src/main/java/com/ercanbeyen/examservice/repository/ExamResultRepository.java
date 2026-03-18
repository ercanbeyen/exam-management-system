package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.entity.ExamResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, String> {
    @Query(value = """
           SELECT es
           FROM ExamResult es
           INNER JOIN es.examRegistration er
           WHERE er.candidateId = :candidateId
           """)
    Page<ExamResult> findAllByCandidateId(@Param("candidateId") String candidateId, Pageable pageable);
    boolean existsByExamRegistration(ExamRegistration examRegistration);
}
