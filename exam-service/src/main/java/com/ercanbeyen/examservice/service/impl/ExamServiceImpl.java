package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.embeddable.ExamTime;
import com.ercanbeyen.examservice.mapper.ExamMapper;
import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.repository.ExamRepository;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.examservice.entity.Exam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final ExamMapper examMapper;

    @Override
    public ExamDto createExam(ExamDto request) {
        Exam exam = examMapper.dtoToEntity(request);
        return examMapper.entityToDto(examRepository.save(exam));
    }

    @Override
    public ExamDto updateExam(String id, ExamDto request) {
        Exam exam = findById(id);
        ExamTime examTime = new ExamTime(request.time().date(), request.time().startedAt(), request.time().finishedAt());

        exam.setSubject(request.subject());
        exam.setTime(examTime);

        return examMapper.entityToDto(examRepository.save(exam));
    }

    @Override
    public ExamDto getExam(String id) {
        return examMapper.entityToDto(findById(id));
    }

    @Override
    public List<ExamDto> getExams() {
        List<ExamDto> examDtos = new ArrayList<>();

        examRepository.findAll()
                .forEach(exam -> examDtos.add(examMapper.entityToDto(exam)));

        return examDtos;
    }

    @Override
    public String deleteExam(String id) {
        examRepository.deleteById(id);
        return String.format("Exam %s is successfully deleted", id);
    }

    @Override
    public Exam findById(String id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Exam %s is not found", id)));

        log.info("Exam {} is found", id);

        return exam;
    }
}
