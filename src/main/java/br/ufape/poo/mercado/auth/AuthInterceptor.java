package br.ufape.poo.mercado.auth;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String metodo = request.getMethod();
        String caminho = request.getRequestURI();

        if (HttpMethod.OPTIONS.matches(metodo)
                || caminho.equals("/api/auth/login")
                || caminho.equals("/error")
                || (HttpMethod.GET.matches(metodo) && caminho.startsWith("/api/produtos"))
                || (HttpMethod.GET.matches(metodo) && caminho.equals("/produtos"))) {
            return true;
        }

        String token = extrairToken(request.getHeader("Authorization"));
        try {
            authService.validar(token);
            return true;
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"Acesso administrativo necessário.\"}");
            return false;
        }
    }

    private String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) return null;
        return authorization.substring(7).trim();
    }
}
