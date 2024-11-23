package com.ercanbeyen.candidateservice.service.impl;

import com.ercanbeyen.candidateservice.entity.Candidate;
import com.ercanbeyen.servicecommon.client.SchoolServiceClient;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.candidateservice.mapper.CandidateMapper;
import com.ercanbeyen.candidateservice.repository.CandidateRepository;
import com.ercanbeyen.candidateservice.service.CandidateService;
import com.ercanbeyen.servicecommon.client.exception.ResourceNotFoundException;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final SchoolServiceClient schoolServiceClient;

    @Override
    public CandidateDto createCandidate(CandidateDto request) {
        ResponseEntity<SchoolDto> schoolServiceResponse = schoolServiceClient.getSchool(request.schoolId());
        log.info(LogMessage.CLIENT_SERVICE_RESPONSE, "School", schoolServiceResponse);

        Candidate candidate = candidateMapper.dtoToEntity(request);
        candidate.setSchoolId(request.schoolId());

        return candidateMapper.entityToDto(candidateRepository.save(candidate));
    }

    @Override
    public CandidateDto updateCandidate(String id, CandidateDto request) {
        Candidate candidateInDb = findById(id);

        ResponseEntity<SchoolDto> schoolServiceResponse = schoolServiceClient.getSchool(request.schoolId());
        log.info(LogMessage.CLIENT_SERVICE_RESPONSE, "School", schoolServiceResponse);

        candidateInDb.setSchoolId(request.schoolId());
        candidateInDb.setName(request.name());
        candidateInDb.setAge(request.age());
        candidateInDb.setGender(request.gender());

        return candidateMapper.entityToDto(candidateRepository.save(candidateInDb));
    }

    @Override
    public CandidateDto getCandidate(String id) {
        return candidateMapper.entityToDto(findById(id));
    }

    @Override
    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll()
                .stream()
                .map(candidateMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteCandidate(String id) {
        candidateRepository.deleteById(id);
        return String.format("Candidate %s is successfully deleted", id);
    }

    private Candidate findById(String id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Candidate %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "Candidate", id);

        return candidate;
    }
}
