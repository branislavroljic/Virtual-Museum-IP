package org.unibl.etf.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.models.requests.AuthenticationRequest;
import org.unibl.etf.models.requests.RefreshTokenRequest;
import org.unibl.etf.models.requests.RegistrationRequest;
import org.unibl.etf.models.responses.AuthenticationResponse;
import org.unibl.etf.models.responses.RefreshTokenResponse;
import org.unibl.etf.services.AuthService;
import org.unibl.etf.services.LogService;
import org.unibl.etf.services.UserService;

import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;
    ;
    private final UserService userService;
    private final LogService logService;

    public AuthenticationController(AuthService authService, UserService userService, LogService logService) {
        this.authService = authService;
        this.userService = userService;
        this.logService = logService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse response = authService.login(request);
        logService.log(request.getUsername(), "LOGIN", "User logged in!", new Timestamp(System.currentTimeMillis()));
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) {
        userService.register(request);
        logService.log(request.getUsername(), "REGISTER", "User registered successfully!",
                new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(Authentication principal){
        authService.logout(principal);
        logService.log(principal.getName(), "LOGOUT", "User logged out!", new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh_token")
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request, Authentication principal) {
        return authService.refreshToken(request);
    }

    @GetMapping("/token")
    public ResponseEntity<String> getOtpForAdminApp(Authentication principal) {
        logService.log(principal.getName(), "ADMIN_APP", "Admin requested otp for Admin app!",
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(authService.generateOtpForAdmin(principal));
    }
}
