package com.ercanbeyen.examservice.service.impl;

import com.ercanbeyen.examservice.client.CandidateClient;
import com.ercanbeyen.examservice.constant.message.ResponseMessage;
import com.ercanbeyen.examservice.dto.ExamResultDto;
import com.ercanbeyen.examservice.dto.response.ExamResultResponse;
import com.ercanbeyen.examservice.entity.ExamRegistration;
import com.ercanbeyen.examservice.entity.ExamResult;
import com.ercanbeyen.examservice.mapper.ExamResultMapper;
import com.ercanbeyen.examservice.repository.ExamResultRepository;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.examservice.service.ExamResultService;
import com.ercanbeyen.servicecommon.client.exception.ResourceConflictException;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.message.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamResultServiceImpl implements ExamResultService {
    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper examResultMapper;
    private final ExamRegistrationService examRegistrationService;
    private final CandidateClient candidateClient;

    @Override
    public ExamResultDto createExamResult(ExamResultDto request) {
        ExamResult examResult = examResultMapper.dtoToEntity(request);
        ExamRegistration examRegistration = examRegistrationService.findById(request.examRegistrationId());

        if (examResultRepository.existsByExamRegistration(examRegistration)) {
            throw new ResourceConflictException(ResponseMessage.RESULT_AVAILABLE_FOR_REGISTRATION);
        }

        examResult.setExamRegistration(examRegistration);
        examResult.setAnnouncedAt(LocalDateTime.now());

        return examResultMapper.entityToDto(examResultRepository.save(examResult));
    }

    @Override
    public ExamResultDto updateExamResult(String id, ExamResultDto request) {
        ExamResult examResult = findById(id);
        String requestedExamRegistrationId = request.examRegistrationId();

        if (!examResult.getExamRegistration().getId().equals(requestedExamRegistrationId)) {
            ExamRegistration examRegistration = examRegistrationService.findById(requestedExamRegistrationId);

            if (examResultRepository.existsByExamRegistration(examRegistration)) {
                throw new ResourceConflictException(ResponseMessage.RESULT_AVAILABLE_FOR_REGISTRATION);
            }

            examResult.setExamRegistration(examRegistration);
        }

        examResult.setCandidateId(request.candidateId());
        examResult.setScore(request.score());

        return examResultMapper.entityToDto(examResultRepository.save(examResult));
    }

    @Override
    public ExamResultDto getExamResult(String id, String username) {
        ExamResult examResult = findById(id);
        candidateClient.checkCandidate(examResult.getCandidateId(), username);
        return examResultMapper.entityToDto(examResult);
    }

    @Override
    public List<ExamResultDto> getExamResults(String subject, String candidateUsername) {
        String candidateId = Optional.ofNullable(candidateUsername).isPresent() ? candidateClient.getCandidateIdByUsername(candidateUsername) : StringUtils.EMPTY;
        Predicate<ExamResult> examResultPredicate = examResult ->
                (candidateId.equals(StringUtils.EMPTY) || (examResult.getCandidateId().equals(candidateId))
                        && examResult.getExamRegistration()
                        .getExamEvent()
                        .getExam()
                        .getSubject()
                        .equals(subject));

        Comparator<ExamResult> examResultComparator = Comparator.comparing(ExamResult::getScore)
                .reversed()
                .thenComparing(ExamResult::getCandidateId);

        return examResultRepository.findAll()
                .stream()
                .filter(examResultPredicate)
                .sorted(examResultComparator)
                .map(examResultMapper::entityToDto)
                .toList();
    }

    @Override
    public Page<ExamResultResponse> getExamResultsOfCandidate(String candidateId, int pageNumber, int pageSize) {
        Sort sort = Sort.by("announcedAt").descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return examResultRepository.findAllByCandidateId(candidateId, pageable)
                .map(examResult -> new ExamResultResponse(
                        examResult.getId(),
                        examResult.getExamRegistration()
                                .getExamEvent()
                                .getExam()
                                .getSubject(),
                        examResult.getCandidateId(),
                        examResult.getScore(),
                        examResult.getAnnouncedAt()));
    }

    @Override
    public String deleteExamResult(String id) {
        ExamResult examResult = findById(id);
        examResultRepository.delete(examResult);
        return "Exam result is successfully deleted";
    }

    private ExamResult findById(String id) {
        ExamResult examResult = examResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam result is not found"));

        log.info(LogMessage.RESOURCE_FOUND, "Exam result", id);

        return examResult;
    }
}
