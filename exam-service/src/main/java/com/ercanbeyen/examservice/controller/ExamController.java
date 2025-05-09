package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamDto;
import com.ercanbeyen.examservice.service.ExamService;
import com.ercanbeyen.examservice.client.AuthClient;
import com.ercanbeyen.examservice.validator.ExamValidator;
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
@RequestMapping("/exams")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamController {
    private final ExamService examService;
    private final AuthClient authClient;

    @Operation(summary = "Create exam")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam is successfully created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamDto.class)
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
                                    value = "{ \"subject\": \"Subject is mandatory\"}"
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
            )
    })
    @PostMapping
    public ResponseEntity<ExamDto> createExam(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam to create",
                    required = true
            ) @RequestBody @Valid ExamDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        ExamValidator.checkExamTime(request);
        return ResponseEntity.ok(examService.createExam(request));
    }

    @Operation(summary = "Update exam by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam is successfully updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamDto.class)
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
                                    value = "{ \"subject\": \"Subject is mandatory\"}"
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
                    description = "Exam is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam is not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExamDto> updateExam(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam",
                    required = true
            ) @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam to update",
                    required = true
            ) @RequestBody @Valid ExamDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        ExamValidator.checkExamTime(request);
        return ResponseEntity.ok(examService.updateExam(id, request));
    }

    @Operation(summary = "Get exam by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam is successfully fetched",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamDto.class)
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
                    description = "Exam is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExamDto> getExam(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examService.getExam(id));
    }

    @Operation(summary = "Get exams")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exams are successfully fetched"
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
    public ResponseEntity<List<ExamDto>> getExams(
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examService.getExams());
    }

    @Operation(summary = "Delete exam by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam is successfully deleted",
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
                    description = "Exam is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExam(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examService.deleteExam(id));
    }
}
