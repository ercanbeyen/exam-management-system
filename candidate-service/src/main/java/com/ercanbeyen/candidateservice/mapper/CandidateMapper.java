package com.ercanbeyen.candidateservice.mapper;

import com.ercanbeyen.candidateservice.entity.Candidate;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateDto entityToDto(Candidate candidate);
    Candidate dtoToEntity(CandidateDto candidateDto);
}
