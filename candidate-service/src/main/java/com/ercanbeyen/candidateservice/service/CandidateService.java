package com.ercanbeyen.candidateservice.service;

import com.ercanbeyen.servicecommon.client.contract.CandidateDto;

import java.util.List;

public interface CandidateService {
    CandidateDto createCandidate(CandidateDto request);
    CandidateDto updateCandidate(String id, CandidateDto request, String username);
    CandidateDto getCandidate(String id, String username);
    List<CandidateDto> getCandidates();
    String deleteCandidate(String id, String username);
}
