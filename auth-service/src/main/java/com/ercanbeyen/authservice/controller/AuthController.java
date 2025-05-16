package com.ercanbeyen.authservice.controller;

import com.ercanbeyen.authservice.request.LoginRequest;
import com.ercanbeyen.authservice.request.RegistrationRequest;
import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.service.AuthService;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "Authorization")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User is successfully registered",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegistrationRequest.class)
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
                                    value = "{ \"username\": \"Username is mandatory\", \"password\": \"Password is mandatory\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User credentials conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"User already exists\" }"
                            )
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponse<String>> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User to create",
                    required = true
            ) @RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.ok(new MessageResponse<>(authService.register(request)));
    }

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tokens are successfully created"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Incorrect user credentials provided",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Invalid username or password\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Username is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1002\", \"message\": \"Username is not found\" }"
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create tokens to login",
                    required = true
            ) @RequestBody @Valid LoginRequest request, HttpServletResponse servletResponse) {
        authService.login(request, servletResponse);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tokens are successfully refreshed"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Refresh token is revoked",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"Refresh token has already been revoked\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Refresh token is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1002\", \"message\": \"Refresh token is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/token-refresh")
    public ResponseEntity<Void> refreshToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        authService.refreshToken(servletRequest, servletResponse);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Validate token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tokens are successfully validated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Access token is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1002\", \"message\": \"Access token is not found\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Token is already revoked",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"417\", \"errorCode\": \"AUTH-SERVICE-1005\", \"message\": \"Token is already expired\" }"
                            )
                    )
            )
    })
    @GetMapping("/validate")
    public ResponseEntity<MessageResponse<String>> validateToken(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Access token of the user",
                    required = true
            ) @RequestParam("token") String token) {
        log.info("We are in AuthController::validateToken");
        authService.validateToken(token);
        return ResponseEntity.ok(new MessageResponse<>("Token is valid"));
    }

    @Operation(summary = "Update user credentials")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User credentials are successfully updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponse.class)
                            ),

                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User credentials conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"403\", \"errorCode\": \"AUTH-SERVICE-1004\", \"message\": \"User already exists\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User credential is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1002\", \"message\": \"User credential does not exist\" }"
                            )
                    )
            )
    })
    @PutMapping("/{username}/roles")
    public ResponseEntity<MessageResponse<String>> updateUserCredential(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Username of the user",
                    required = true
            ) @PathVariable("username") String username,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials to update",
                    required = true
            ) @RequestBody UserCredential userCredential) {
        return ResponseEntity.ok(new MessageResponse<>(authService.updateUserCredential(username, userCredential)));
    }

    @Operation(summary = "Get user roles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Roles of user are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1002\", \"message\": \"Username is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{username}/roles")
    public List<String> getRoles(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Username of the user",
                    required = true
            ) @PathVariable("username") String username) {
        return authService.getRoles(username);
    }

    @Operation(summary = "Get authorities of user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authorities of user are successfully fetched"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"httpStatus\": \"404\", \"errorCode\": \"AUTH-SERVICE-1002\", \"message\": \"Username is not found\" }"
                            )
                    )
            )
    })
    @GetMapping("/{username}/authorities")
    public List<GrantedAuthority> getAuthorities(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Username of the user",
                    required = true
            ) @PathVariable("username") String username) {
        return authService.getAuthorities(username);
    }

    @Operation(summary = "Get username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Username is successfully extracted"
            )
    })
    @GetMapping("/extract/username")
    public ResponseEntity<String> extractUsername(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Extract username of the user",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class)
                            ),

                    }
            ) @RequestParam("token") String token) {
        return ResponseEntity.ok(authService.extractUsername(token));
    }

    @Operation(summary = "Check requested role from the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role is found"
            )
    })
    @GetMapping("/check-role")
    public ResponseEntity<Boolean> checkRole(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Access token of the user",
                    required = true
            ) @RequestParam("token") String token,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Requested role to check",
                    required = true
            ) @RequestParam("role") String role) {
        log.info("We are in AuthController::checkRole. Role {}", role);
        return ResponseEntity.ok(authService.checkRole(token, role));
    }

    @Operation(summary = "Check requested role from the user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role is found"
            )
    })
    @GetMapping("/{username}/check-role")
    public ResponseEntity<Boolean> checkUserRole(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Username of the user",
                    required = true
            ) @PathVariable("username") String username,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Requested role to check",
                    required = true
            ) @RequestParam("role") String role) {
        log.info("We are in AuthController::checkUserRole. Requested role {}", role);
        boolean response = authService.checkUserRole(username, role);
        log.info("Response: {}", response);
        return ResponseEntity.ok(response);
    }
}
