package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamRegistrationDto;
import com.ercanbeyen.examservice.dto.response.ExamEntry;
import com.ercanbeyen.examservice.service.ExamRegistrationService;
import com.ercanbeyen.examservice.client.AuthClient;
import com.ercanbeyen.servicecommon.client.exception.response.ErrorResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-registrations")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamRegistrationController {
    private final ExamRegistrationService examRegistrationService;
    private final AuthClient authClient;

    @Operation(summary = "Create exam registration")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam registration is successfully created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamRegistrationDto.class)
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
    public ResponseEntity<ExamRegistrationDto> createExamRegistration(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam registration to create",
                    required = true
            ) @RequestBody @Valid ExamRegistrationDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.createExamRegistration(request, username));
    }

    @Operation(summary = "Update exam registration by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam registration is successfully updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamRegistrationDto.class)
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
                    description = "Exam registration is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam registration is not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExamRegistrationDto> updateExamRegistration(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam registration",
                    required = true
            ) @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam registration to update",
                    required = true
            ) @RequestBody @Valid ExamRegistrationDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.updateExamRegistration(id, request, username));
    }

    @Operation(summary = "Get exam registration by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam registration is successfully fetched",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamRegistrationDto.class)
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
                    description = "Exam registration is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam registration is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExamRegistrationDto> getExamRegistration(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam registration",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.getExamRegistration(id, username));
    }

    @Operation(summary = "Get exam registrations")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam registrations are successfully fetched"
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
    public ResponseEntity<List<ExamRegistrationDto>> getExamRegistrations(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Username of the candidate",
                    required = true
            ) @RequestParam("user") String candidateUsername,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String loggedInUsername) {
        authClient.checkLoggedInUser(candidateUsername, loggedInUsername);
        return ResponseEntity.ok(examRegistrationService.getExamRegistrations(candidateUsername));
    }

    @Operation(summary = "Get exam entries")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam entries are successfully fetched",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
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
            )
    })
    @GetMapping("/candidates")
    public ResponseEntity<List<ExamEntry>> getExamEntries(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Id of the exam event",
                    required = true
            ) @RequestParam("exam-event") String examEventId,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String loggedInUsername) {
        return ResponseEntity.ok(examRegistrationService.getExamEntries(examEventId, loggedInUsername));
    }

    @Operation(summary = "Delete exam registration by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam registration is successfully deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
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
                    description = "Exam registration is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam registration is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamRegistration(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam registration",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examRegistrationService.deleteExamRegistration(id, username));
    }
}
