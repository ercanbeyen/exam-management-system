package com.ercanbeyen.examservice.repository;

import com.ercanbeyen.examservice.embeddable.ExamLocation;
import com.ercanbeyen.examservice.entity.ExamEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamEventRepository extends JpaRepository<ExamEvent, String> {
    @Query(value = """
           SELECT ev
           FROM ExamEvent ev
           INNER JOIN ev.exam e
           WHERE ev.location = :examLocation AND e.examPeriod.date = :examDate
           """)
    List<ExamEvent> findAllByExamLocationAndExamPeriod(
            @Param("examLocation") ExamLocation examLocation,
            @Param("examDate") LocalDate examDate
    );

    @Query(value = """
           SELECT ev
           FROM ExamEvent ev
           INNER JOIN ev.exam e
           WHERE e.subject = :examSubject AND ev.location = :examLocation AND e.examPeriod.date = :examDate
           """)
    Optional<ExamEvent> findByExamSubjectAndExamLocationAndExamPeriod(
            @Param("examSubject") String examSubject,
            @Param("examLocation") ExamLocation examLocation,
            @Param("examDate") LocalDate examDate
    );
}
