package com.ercanbeyen.candidateservice.controller;

import com.ercanbeyen.candidateservice.client.AuthClient;
import com.ercanbeyen.servicecommon.client.contract.CandidateDto;
import com.ercanbeyen.candidateservice.service.CandidateService;
import com.ercanbeyen.servicecommon.client.exception.response.ErrorResponse;
import com.ercanbeyen.servicecommon.client.logging.LogMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Authorization")
public class CandidateController {
    private final CandidateService candidateService;
    private final AuthClient authClient;

    @Operation(summary = "Create candidate")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidate is successfully created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CandidateDto.class)
                            ),

                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input provided",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"username\": \"Username is mandatory\", \"password\": \"School name is mandatory\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized access",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"SCHOOL-SERVICE-1002\", \"message\": \"School is not found\" }"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Candidate to create",
                    required = true
            ) @RequestBody @Valid CandidateDto request) {
        return ResponseEntity.ok(candidateService.createCandidate(request));
    }

    @Operation(summary = "Update candidate by his/her id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidate is successfully updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"username\": \"Username is mandatory\", \"schoolName\": \"School name is mandatory\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"CANDIDATE-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Candidate is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"CANDIDATE-SERVICE-1002\", \"message\": \"Candidate is not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the candidate",
                    required = true
            ) @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Candidate to update",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CandidateDto.class)
                    )
            ) @RequestBody @Valid CandidateDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, request, username));
    }

    @Operation(summary = "Get candidate by his/her id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidate is successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"CANDIDATE-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Candidate is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"CANDIDATE-SERVICE-1002\", \"message\": \"Candidate is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the candidate",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        log.info(LogMessage.LOGGED_IN_USER, username);
        return ResponseEntity.ok(candidateService.getCandidate(id, username));
    }

    @Operation(summary = "Get candidate by his/her username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidate is successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"CANDIDATE-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Candidate is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"CANDIDATE-SERVICE-1002\", \"message\": \"Candidate is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/users/{username}")
    public ResponseEntity<CandidateDto> getCandidateByUsername(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Username of the candidate",
                    required = true
            ) @PathVariable("username") String username,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String loggedInUsername) {
        log.info(LogMessage.LOGGED_IN_USER, loggedInUsername);
        return ResponseEntity.ok(candidateService.getCandidateByUsername(username, loggedInUsername));
    }

    @Operation(summary = "Get candidates")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidates are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"CANDIDATE-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<CandidateDto>> getCandidates(
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(candidateService.getCandidates());
    }

    @Operation(summary = "Delete candidate by his/her id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidate is successfully deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"CANDIDATE-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Candidate is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"CANDIDATE-SERVICE-1002\", \"message\": \"Candidate is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the candidate",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(candidateService.deleteCandidate(id, username));
    }
}
