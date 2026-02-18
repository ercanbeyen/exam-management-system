package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.client.AuthClient;
import com.ercanbeyen.examservice.client.CandidateClient;
import com.ercanbeyen.examservice.dto.ExamResultDto;
import com.ercanbeyen.examservice.service.ExamResultService;
import com.ercanbeyen.servicecommon.client.exception.response.ErrorResponse;
import com.ercanbeyen.servicecommon.client.response.MessageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-results")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamResultController {
    private final ExamResultService examResultService;
    private final AuthClient authClient;
    private final CandidateClient candidateClient;

    @Operation(summary = "Create exam result")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam result is successfully created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamResultDto.class)
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
                                    value = "{ \"candidateId\": \"Candidate id is mandatory\"}"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ExamResultDto> createExamResult(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam result to create",
                    required = true
            ) @RequestBody @Valid ExamResultDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.createExamResult(request));
    }

    @Operation(summary = "Update exam result by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam result is successfully updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamResultDto.class)
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
                                    value = "{ \"candidateId\": \"Candidate id is mandatory\"}"
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
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Exam result is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam result is not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExamResultDto> updateExamResult(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam result",
                    required = true
            ) @PathVariable("id") String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam result to update",
                    required = true
            ) @RequestBody @Valid ExamResultDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.updateExamResult(id, request));
    }

    @Operation(summary = "Get exam result by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam result is successfully fetched",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamResultDto.class)
                            ),

                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized access",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Exam result is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam result is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExamResultDto> getExamResult(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam result",
                    required = true
            )
            @PathVariable("id") String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            )
            @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examResultService.getExamResult(id, username));
    }

    @Operation(summary = "Get exam results")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam results are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized access",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ExamResultDto>> getExamResults(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Subject of the exam",
                    required = true
            ) @RequestParam("subject") String subject,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examResultService.getExamResults(subject));
    }

    @Operation(summary = "Get exam results of the candidate")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam results are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized access",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            )
    })
    @GetMapping("/candidates/{candidateId}")
    public ResponseEntity<Page<ExamResultDto>> getExamResultsOfCandidate(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the candidate",
                    required = true
            ) @PathVariable("candidateId") String candidateId,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Page number"
            ) @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Page size"
            ) @RequestParam(name = "size", defaultValue = "5") Integer pageSize,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        candidateClient.checkCandidate(candidateId, username);
        return ResponseEntity.ok(examResultService.getExamResultsOfCandidate(candidateId, pageNumber, pageSize));
    }

    @Operation(summary = "Delete exam result by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam result is successfully deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class)
                            ),

                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized access",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Exam result is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam result is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteExamResult(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam result",
                    required = true
            ) @PathVariable("id") String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(new MessageResponse<>(examResultService.deleteExamResult(id)));
    }
}
