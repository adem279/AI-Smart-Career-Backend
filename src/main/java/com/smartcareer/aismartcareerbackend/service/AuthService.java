package com.smartcareer.aismartcareerbackend.service;

import com.smartcareer.aismartcareerbackend.dto.LoginRequest;
import com.smartcareer.aismartcareerbackend.dto.RegisterCandidateRequest;
import com.smartcareer.aismartcareerbackend.dto.RegisterCompanyRequest;
import com.smartcareer.aismartcareerbackend.dto.LoginResponse;
import com.smartcareer.aismartcareerbackend.entities.Candidate;
import com.smartcareer.aismartcareerbackend.entities.Company;
import com.smartcareer.aismartcareerbackend.entities.Role;
import com.smartcareer.aismartcareerbackend.entities.User;
import com.smartcareer.aismartcareerbackend.repository.RoleRepository;
import com.smartcareer.aismartcareerbackend.repository.UserRepository;
import com.smartcareer.aismartcareerbackend.security.CustomUserDetails;
import com.smartcareer.aismartcareerbackend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponse registerCandidate(RegisterCandidateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        Role role = roleRepository.findByName("CANDIDATE")
                .orElseThrow(() -> new RuntimeException("Rôle CANDIDATE introuvable, initialise-le en base"));

        Candidate candidate = Candidate.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .enabled(true)
                .role(role)
                .build();

        userRepository.save(candidate);

        String token = jwtUtil.generateToken(candidate.getEmail(), candidate.getId(), "CANDIDATE");
        return LoginResponse.builder()
                .token(token)
                .userId(candidate.getId())
                .email(candidate.getEmail())
                .userType("CANDIDATE")
                .build();
    }

    public LoginResponse registerCompany(RegisterCompanyRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        Role role = roleRepository.findByName("COMPANY")
                .orElseThrow(() -> new RuntimeException("Rôle COMPANY introuvable, initialise-le en base"));

        Company company = Company.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .enabled(true)
                .role(role)
                .companyName(request.getCompanyName())
                .sector(request.getSector())
                .build();

        userRepository.save(company);

        String token = jwtUtil.generateToken(company.getEmail(), company.getId(), "COMPANY");
        return LoginResponse.builder()
                .token(token)
                .userId(company.getId())
                .email(company.getEmail())
                .userType("COMPANY")
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), userDetails.getUserType());

        return LoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .userType(userDetails.getUserType())
                .build();
    }
}