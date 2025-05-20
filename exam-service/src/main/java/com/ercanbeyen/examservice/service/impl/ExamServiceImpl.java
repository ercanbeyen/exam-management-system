package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.mapper.ExamMapper;
import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.repository.ExamRepository;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        exam.setExamPeriod(request.period());

        return examMapper.entityToDto(examRepository.save(exam));
    }

    @Override
    public ExamDto getExam(String id) {
        return examMapper.entityToDto(findById(id));
    }

    @Override
    public List<ExamDto> getExams() {
        return examRepository.findAll()
                .stream()
                .map(examMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteExam(String id) {
        examRepository.deleteById(id);
        return "Exam is successfully deleted";
    }

    @Override
    public Exam findById(String id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam is not found"));

        log.info(LogMessage.RESOURCE_FOUND, "Exam", id);

        return exam;
    }

    @Override
    public Exam findBySubject(String subject) {
        Exam exam = examRepository.findBySubject(subject)
                .orElseThrow(() -> new ResourceNotFoundException("Exam is not found"));

        log.info(LogMessage.RESOURCE_FOUND, "Exam", subject);

        return exam;
    }
}
