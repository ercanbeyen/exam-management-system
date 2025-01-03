package com.ercanbeyen.candidateservice.service;

import com.ercanbeyen.servicecommon.client.contract.CandidateDto;

import java.util.List;

public interface CandidateService {
    CandidateDto createCandidate(CandidateDto request);
    CandidateDto updateCandidate(String id, CandidateDto request);
    CandidateDto getCandidate(String id);
    List<CandidateDto> getCandidates();
    String deleteCandidate(String id);
}
