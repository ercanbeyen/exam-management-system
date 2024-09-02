package com.ercanbeyen.schoolservice.repository;

import com.ercanbeyen.schoolservice.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {

}
