package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, String> {

}
