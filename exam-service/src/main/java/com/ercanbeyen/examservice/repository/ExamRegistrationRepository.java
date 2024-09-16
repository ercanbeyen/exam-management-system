package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.entity.ExamRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, String> {

}
