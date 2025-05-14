package com.ercanbeyen.notificationservice.controller;

import com.ercanbeyen.notificationservice.service.NotificationService;
import com.ercanbeyen.notificationservice.client.AuthClient;
import com.ercanbeyen.servicecommon.client.exception.response.ErrorResponse;
import com.ercanbeyen.servicecommon.client.messaging.NotificationDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class NotificationController {
    private final NotificationService notificationService;
    private final AuthClient authClient;

    @Operation(summary = "Get notification by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification is successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"NOTIFICATION-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"NOTIFICATION-SERVICE-1002\", \"message\": \"Notification is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotification(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the notification",
                    required = true
            ) @PathVariable("id") String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(notificationService.getNotification(id, username));
    }

    @Operation(summary = "Get notifications of the candidate")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notifications are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"NOTIFICATION-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Username of the user that notification belongs to",
                    required = true
            ) @RequestParam(value = "user") String notificationUsername,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String loggedInUsername) {
        authClient.checkLoggedInUser(notificationUsername, loggedInUsername);
        return ResponseEntity.ok(notificationService.getNotifications(notificationUsername));
    }

    @Operation(summary = "Delete notification by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification is successfully deleted",
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
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"NOTIFICATION-SERVICE-1003\", \"message\": \"Unauthorized access\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"NOTIFICATION-SERVICE-1002\", \"message\": \"Notification is not found\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteNotification(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Id of the notification",
                    required = true
            ) @PathVariable("id") String id,
            @Parameter(
                    in = ParameterIn.HEADER,
                    description = "Username of the logged in user",
                    required = true
            ) @RequestHeader("loggedInUser") String username) {
        return ResponseEntity.ok(new MessageResponse<>(notificationService.deleteNotification(id, username)));
    }
}
