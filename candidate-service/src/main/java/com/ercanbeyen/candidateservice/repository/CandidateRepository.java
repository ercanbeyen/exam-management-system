package com.ercanbeyen.candidateservice.repository;

import com.ercanbeyen.candidateservice.entity.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

}
