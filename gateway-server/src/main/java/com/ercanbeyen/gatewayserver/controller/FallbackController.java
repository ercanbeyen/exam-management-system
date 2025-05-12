package com.ercanbeyen.gatewayserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fb")
public class FallbackController {
    @Operation(summary = "Send response after fallbacks from exam service")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response is successfully sent",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            )
    })
    @GetMapping("/exam")
    public ResponseEntity<String> examFallback() {
        return ResponseEntity.ok("Exam Service is unavailable");
    }

    @Operation(summary = "Send response after fallbacks from school service")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response is successfully sent",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            )
    })
    @GetMapping("/school")
    public ResponseEntity<String> schoolFallback() {
        return ResponseEntity.ok("School Service is unavailable");
    }

    @Operation(summary = "Send response after fallbacks from candidate service")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response is successfully sent",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            )
    })
    @GetMapping("/candidate")
    public ResponseEntity<String> candidateFallback() {
        return ResponseEntity.ok("Candidate Service is unavailable");
    }

    @Operation(summary = "Send response after fallbacks from notification service")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response is successfully sent",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            )
    })
    @GetMapping("/notification")
    public ResponseEntity<String> notificationFallback() {
        return ResponseEntity.ok("Notification Service is unavailable");
    }

    @Operation(summary = "Send response after fallbacks from auth service")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response is successfully sent",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            )
    })
    @GetMapping("/auth")
    public ResponseEntity<String> authFallback() {
        return ResponseEntity.ok("Auth Service is unavailable");
    }
}
