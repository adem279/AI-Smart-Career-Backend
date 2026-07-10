package com.smartcareer.aismartcareerbackend.controllers;
import com.smartcareer.aismartcareerbackend.dto.LoginRequest;
import com.smartcareer.aismartcareerbackend.dto.RegisterCandidateRequest;
import com.smartcareer.aismartcareerbackend.dto.RegisterCompanyRequest;
import com.smartcareer.aismartcareerbackend.dto.LoginResponse;
import com.smartcareer.aismartcareerbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/candidate")
    public ResponseEntity<LoginResponse> registerCandidate(@Valid @RequestBody RegisterCandidateRequest request) {
        return ResponseEntity.ok(authService.registerCandidate(request));
    }

    @PostMapping("/register/company")
    public ResponseEntity<LoginResponse> registerCompany(@Valid @RequestBody RegisterCompanyRequest request) {
        return ResponseEntity.ok(authService.registerCompany(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
