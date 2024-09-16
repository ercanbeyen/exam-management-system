package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.dto.ExamLocationDto;
import com.ercanbeyen.examservice.embeddable.ExamLocation;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.mapper.ExamEventMapper;
import com.ercanbeyen.examservice.repository.ExamEventRepository;
import com.ercanbeyen.examservice.service.ExamEventNotificationService;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.servicecommon.client.SchoolServiceClient;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExamEventServiceImpl implements ExamEventService {
    private final ExamEventRepository examEventRepository;
    private final ExamEventMapper examEventMapper;
    private final ExamService examService;
    private final SchoolServiceClient schoolServiceClient;
    private final ExamEventNotificationService examEventNotificationService;

    @Override
    public ExamEventDto createExamEvent(ExamEventDto request) {
        ExamEvent constructExamEvent = constructExamEvent(null, request);
        ExamEvent savedExamEvent = examEventRepository.save(constructExamEvent);

        ExamEventDto examEventDto = examEventMapper.entityToDto(savedExamEvent);
        examEventNotificationService.sendToQueue(examEventDto);

        return examEventDto;
    }

    @Override
    public ExamEventDto updateExamEvent(String id, ExamEventDto request) {
        ExamEvent examEvent = constructExamEvent(id, request);
        return examEventMapper.entityToDto(examEventRepository.save(examEvent));
    }

    @Override
    public ExamEventDto getExamEvent(String id) {
        return examEventMapper.entityToDto(findById(id));
    }

    @Override
    public List<ExamEventDto> getExamEvents() {
        List<ExamEventDto> examEventDtos = new ArrayList<>();

        examEventRepository.findAll()
                .forEach(examEvent -> examEventDtos.add(examEventMapper.entityToDto(examEvent)));

        return examEventDtos;
    }

    @Override
    public String deleteExamEvent(String id) {
        ExamEvent examEvent = findById(id);
        examEventRepository.delete(examEvent);
        return String.format("Exam event %s is successfully deleted", id);
    }

    @Override
    public ExamEvent findById(String id) {
        ExamEvent examEvent = examEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Exam event %s is not found", id)));

        log.info("Exam event {} is found", id);

        return examEvent;
    }

    private ExamEvent constructExamEvent(String id, ExamEventDto request) {
        ExamEvent examEvent;

        if (Optional.ofNullable(id).isPresent()) {
            examEvent = findById(id);
        } else {
            examEvent = examEventMapper.dtoToEntity(request);
        }

        Exam exam = examService.findById(request.examId());
        ExamLocationDto requestedLocation = request.location();

        ResponseEntity<SchoolDto> schoolResponse = schoolServiceClient.getSchool(requestedLocation.schoolId());
        SchoolDto schoolDto = schoolResponse.getBody();

        assert schoolDto != null;

        boolean classroomIdExists = schoolDto.classroomIds()
                .stream()
                .anyMatch(classroomId -> classroomId.equals(requestedLocation.classroomId()));

        if (!classroomIdExists) {
            throw new RuntimeException(String.format("Classroom %s does not found inside school %d", requestedLocation.classroomId(), requestedLocation.schoolId()));
        }

        ExamLocation examLocation = new ExamLocation(requestedLocation.schoolId(), requestedLocation.classroomId());

        examEvent.setExam(exam);
        examEvent.setLocation(examLocation);

        return examEvent;
    }
}
