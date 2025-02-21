package com.ercanbeyen.candidateservice.service.impl;

import com.ercanbeyen.candidateservice.client.SchoolClient;
import com.ercanbeyen.candidateservice.entity.Candidate;
import com.ercanbeyen.candidateservice.client.AuthClient;
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
    private final SchoolClient schoolClient;
    private final AuthClient authClient;

    @Override
    public CandidateDto createCandidate(CandidateDto request) {
        schoolClient.checkSchool(request);

        Candidate candidate = candidateMapper.dtoToEntity(request);
        candidate.setSchoolName(request.schoolName());

        return candidateMapper.entityToDto(candidateRepository.save(candidate));
    }

    @Override
    public CandidateDto updateCandidate(String id, CandidateDto request, String username) {
        Candidate candidate = findById(id);
        authClient.checkLoggedInUser(candidate.getUsername(), username);

        schoolClient.checkSchool(request);

        candidate.setSchoolName(request.schoolName());
        candidate.setFullName(request.fullName());
        candidate.setAge(request.age());
        candidate.setGender(request.gender());

        return candidateMapper.entityToDto(candidateRepository.save(candidate));
    }

    @Override
    public CandidateDto getCandidate(String id, String username) {
        Candidate candidate = findById(id);
        authClient.checkLoggedInUser(candidate.getUsername(), username);

        return candidateMapper.entityToDto(candidate);
    }

    @Override
    public CandidateDto getCandidateByUsername(String username, String loggedInUsername) {
        Candidate candidate = candidateRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Candidate %s is not found", username)));

        authClient.checkLoggedInUser(username, loggedInUsername);

        return candidateMapper.entityToDto(candidate);
    }

    @Override
    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll()
                .stream()
                .map(candidateMapper::entityToDto)
                .toList();
    }

    @Override
    public String deleteCandidate(String id, String username) {
        candidateRepository.findById(id)
                .ifPresentOrElse(candidate -> {
                    authClient.checkLoggedInUser(candidate.getUsername(), username);
                    candidateRepository.deleteById(id);
                }, () -> {
                    log.error("Candidate {} is not found", id);
                    throw new ResourceNotFoundException("Candidate is not found");
                });

        return String.format("Candidate %s is successfully deleted", id);
    }

    private Candidate findById(String id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Candidate %s is not found", id)));

        log.info(LogMessage.RESOURCE_FOUND, "Candidate", id);

        return candidate;
    }
}
