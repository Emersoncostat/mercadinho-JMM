package br.ufape.poo.mercado.comunicacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufape.poo.mercado.auth.AuthService;
import br.ufape.poo.mercado.comunicacao.dto.request.LoginDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.LoginDTOResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@RequestBody LoginDTORequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginDTOResponse> me(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(authService.validar(extrairToken(authorization)));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        authService.logout(extrairToken(authorization));
        return ResponseEntity.noContent().build();
    }

    private String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) return null;
        return authorization.substring(7).trim();
    }
}
