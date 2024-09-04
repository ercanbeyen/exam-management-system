package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.ExamEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamEventRepository extends JpaRepository<ExamEvent, String> {

}
