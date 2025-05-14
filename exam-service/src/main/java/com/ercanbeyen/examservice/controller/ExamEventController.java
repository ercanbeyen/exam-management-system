package com.ercanbeyen.examservice.controller;

import com.ercanbeyen.examservice.dto.ExamEventDto;
import com.ercanbeyen.examservice.service.ExamEventService;
import com.ercanbeyen.examservice.client.AuthClient;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-events")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class ExamEventController {
    private final ExamEventService examEventService;
    private final AuthClient authClient;

    @Operation(summary = "Create exam event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam event is successfully created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamEventDto.class)
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
                                    value = "{ \"examSubject\": \"Exam subject is mandatory\"}"
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
    public ResponseEntity<ExamEventDto> createExamEvent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam event to create",
                    required = true
            ) @RequestBody @Valid ExamEventDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.createExamEvent(request, username));
    }

    @Operation(summary = "Update exam event by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam event is successfully updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamEventDto.class)
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
                                    value = "{ \"examSubject\": \"Exam subject is mandatory\"}"
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
                    description = "Exam event is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam event is not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExamEventDto> updateExamEvent(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam event",
                    required = true
            ) @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exam event to create",
                    required = true
            ) @RequestBody @Valid ExamEventDto request,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(examEventService.updateExamEvent(id, request, username));
    }

    @Operation(summary = "Get exam event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam event is successfully fetched",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExamEventDto.class)
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
                    description = "Exam event is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam event is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ExamEventDto> getExamEvent(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam event",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examEventService.getExamEvent(id, username));
    }

    @Operation(summary = "Get exam events")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam events are successfully fetched"
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
    public ResponseEntity<List<ExamEventDto>> getExamEvents(
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(examEventService.getExamEvents(username));
    }

    @Operation(summary = "Delete exam event by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exam event is successfully deleted",
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
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Exam event is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"EXAM-SERVICE-1002\", \"message\": \"Exam event is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteExamEvent(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the exam event",
                    required = true
            ) @PathVariable String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        authClient.checkUserHasAdminRole(username);
        return ResponseEntity.ok(new MessageResponse<>(examEventService.deleteExamEvent(id, username)));
    }
}
