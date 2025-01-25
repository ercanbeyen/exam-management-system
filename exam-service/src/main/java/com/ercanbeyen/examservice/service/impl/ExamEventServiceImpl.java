package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.dto.ExamLocationDto;
import com.ercanbeyen.examservice.embeddable.ExamLocation;
import com.ercanbeyen.examservice.embeddable.ExamPeriod;
import com.ercanbeyen.examservice.entity.Exam;
import com.ercanbeyen.examservice.entity.ExamEvent;
import com.ercanbeyen.examservice.mapper.ExamEventMapper;
import com.ercanbeyen.examservice.repository.ExamEventRepository;
import com.ercanbeyen.examservice.service.ExamEventNotificationService;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.servicecommon.client.SchoolServiceClient;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExamEventServiceImpl implements ExamEventService {
    private final ExamEventRepository examEventRepository;
    private final ExamEventMapper examEventMapper;
    private final SchoolServiceClient schoolServiceClient;
    private final ExamService examService;
    private final ExamEventNotificationService examEventNotificationService;


    @Override
    public ExamEventDto createExamEvent(ExamEventDto request, String username) {
        checkExamEventConflicts(request);
        ExamEvent examEvent = constructExamEvent(null, request);
        ExamEvent savedExamEvent = examEventRepository.save(examEvent);

        ExamEventDto examEventDto = examEventMapper.entityToDto(savedExamEvent);
        examEventNotificationService.sendToQueue(examEventDto, username);

        return examEventDto;
    }

    @Override
    public ExamEventDto updateExamEvent(String id, ExamEventDto request, String username) {
        checkExamEventConflicts(request);
        ExamEvent examEvent = constructExamEvent(id, request);
        return examEventMapper.entityToDto(examEventRepository.save(examEvent));
    }

    @Override
    public ExamEventDto getExamEvent(String id, String username) {
        return examEventMapper.entityToDto(findById(id));
    }

    @Override
    public List<ExamEventDto> getExamEvents(String username) {
        return examEventRepository.findAll()
                .stream()
                .map(examEventMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteExamEvent(String id, String username) {
        ExamEvent examEvent = findById(id);
        examEventRepository.delete(examEvent);
        return String.format("Exam event %s is successfully deleted", id);
    }

    @Override
    public ExamEvent findById(String id) {
        ExamEvent examEvent = examEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Exam event %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "Exam event", id);

        return examEvent;
    }

    private ExamEvent constructExamEvent(String id, ExamEventDto request) {
        ExamEvent examEvent = Optional.ofNullable(id).isPresent()
                ? findById(id)
                : examEventMapper.dtoToEntity(request);

        Exam exam = examService.findById(request.examId());
        ExamLocationDto requestedLocation = request.location();

        ResponseEntity<SchoolDto> schoolServiceResponse = schoolServiceClient.getSchool(requestedLocation.schoolId());
        SchoolDto schoolDto = schoolServiceResponse.getBody();

        assert schoolDto != null;

        boolean classroomIdExists = schoolDto.classroomIds()
                .stream()
                .anyMatch(classroomId -> classroomId.equals(requestedLocation.classroomId()));

        if (!classroomIdExists) {
            throw new ResourceNotFoundException(String.format("Classroom %s does not found inside school %d", requestedLocation.classroomId(), requestedLocation.schoolId()));
        }

        ExamLocation examLocation = new ExamLocation(requestedLocation.schoolId(), requestedLocation.classroomId());

        examEvent.setExam(exam);
        examEvent.setLocation(examLocation);

        return examEvent;
    }
    
    private void checkExamEventConflicts(ExamEventDto request) {
        Exam exam = examService.findById(request.examId());
        ExamLocation examLocation = new ExamLocation(request.location().schoolId(), request.location().classroomId());
        ExamPeriod requestedExamPeriod = exam.getExamPeriod();

        List<ExamEvent> examEvents = examEventRepository.findAllByExamLocationAndExamPeriod(examLocation, exam.getExamPeriod().getDate())
                .stream()
                .filter(examEvent -> {
                    ExamPeriod examPeriod = examEvent.getExam().getExamPeriod();
                    LocalTime startTime = examPeriod.getStartTime();
                    LocalTime finishTime = examPeriod.getFinishTime();

                    LocalTime requestedStartTime = requestedExamPeriod.getStartTime();
                    LocalTime requestedFinishTime = requestedExamPeriod.getFinishTime();

                    boolean exactTimeEqualityConflicts = requestedStartTime.equals(startTime) || requestedFinishTime.equals(finishTime);
                    boolean justRequestedStartTimeConflicts  = requestedStartTime.isAfter(startTime)  && requestedFinishTime.isAfter(finishTime)  && requestedStartTime.isBefore(finishTime);   // start requestedStart finish requestedFinish
                    boolean justRequestedFinishTimeConflicts = requestedStartTime.isBefore(startTime) && requestedFinishTime.isBefore(finishTime) && requestedFinishTime.isAfter(startTime);   // requestedStart start requestedFinish finish
                    boolean bothRequestedTimesInnerConflict  = requestedStartTime.isAfter(startTime)  && requestedFinishTime.isBefore(finishTime);                                             // start requestedStart requestedFinish finish
                    boolean bothRequestedTimesOuterConflict  = requestedStartTime.isBefore(startTime) && requestedFinishTime.isAfter(finishTime);                                              // requestedStart start finish requestedFinish

                    log.info("exactTimeEqualityConflicts: {}, justRequestedStartTimeConflicts: {}, justRequestedFinishTimeConflicts: {}, bothRequestedTimesInnerConflict: {}, bothRequestedTimesOuterConflict: {}",
                            exactTimeEqualityConflicts, justRequestedStartTimeConflicts, justRequestedFinishTimeConflicts, bothRequestedTimesInnerConflict, bothRequestedTimesOuterConflict);

                    return exactTimeEqualityConflicts || justRequestedStartTimeConflicts || justRequestedFinishTimeConflicts || bothRequestedTimesInnerConflict || bothRequestedTimesOuterConflict;
                })
                .toList();

        if (!examEvents.isEmpty()) {
            List<String> conflictedExamEventIds = examEvents.stream()
                    .map(ExamEvent::getId)
                    .toList();

            throw new ResourceConflictException("Requested exam event conflicts wih exam events " + conflictedExamEventIds);
        }

        log.info("There is no any conflicts between any exam events");
    }
}
