package com.ercanbeyen.schoolservice.controller;

import com.ercanbeyen.schoolservice.validator.SchoolValidator;
import com.ercanbeyen.servicecommon.client.contract.SchoolDto;
import com.ercanbeyen.schoolservice.service.SchoolService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class SchoolController {
    private final SchoolService schoolService;

    @Operation(summary = "Create school")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "School is successfully created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"name\": \"Name is mandatory\", \"location\": \"Location is mandatory\" }"
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
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"SCHOOL-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<SchoolDto> createSchool(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "School to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SchoolDto.class)
                    )
            ) @RequestBody @Valid SchoolDto request) {
        SchoolValidator.checkClassrooms(request);
        return ResponseEntity.ok(schoolService.createSchool(request));
    }

    @Operation(summary = "Update school by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "School is successfully updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"name\": \"Name is mandatory\", \"location\": \"Location is mandatory\" }"
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
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"SCHOOL-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "School is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"SCHOOL-SERVICE-1004\", \"message\": \"School is not found\" }"
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchool(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the school",
                    required = true
            ) @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "School to update",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SchoolDto.class)
                    )
            ) @RequestBody @Valid SchoolDto request) {
        SchoolValidator.checkClassrooms(request);
        return ResponseEntity.ok(schoolService.updateSchool(id, request));
    }

    @Operation(summary = "Get school by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "School is successfully updated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"SCHOOL-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "School is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"SCHOOL-SERVICE-1004\", \"message\": \"School is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{name}")
    public ResponseEntity<SchoolDto> getSchool(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the school",
                    required = true
            ) @PathVariable String name) {
        SchoolDto school = schoolService.getSchool(name);
        log.info("School is successfully fetched");
        return ResponseEntity.ok(school);
    }

    @Operation(summary = "Get schools")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Schools are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"SCHOOL-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<SchoolDto>> getSchools() {
        return ResponseEntity.ok(schoolService.getSchools());
    }

    @Operation(summary = "Delete school by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "School is successfully updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class)
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
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"SCHOOL-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "School is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"SCHOOL-SERVICE-1004\", \"message\": \"School is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteSchool(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the school",
                    required = true
            ) @PathVariable String id) {
        return ResponseEntity.ok(new MessageResponse<>(schoolService.deleteSchool(id)));
    }
}
