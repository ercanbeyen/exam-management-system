package com.ercanbeyen.examservice.service.impl;

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

        exam.setSubject(request.subject());
        exam.getTime().setDate(request.time().date());
        exam.getTime().setStartedAt(request.time().startedAt());
        exam.getTime().setFinishedAt(request.time().finishedAt());

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
        return "Exam " + id + " is successfully deleted";
    }

    private Exam findById(String id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam is not found"));

        log.info("Exam {} is found", id);

        return exam;
    }
}
