package br.ufape.poo.mercado.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.ufape.poo.mercado.comunicacao.dto.request.LoginDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.LoginDTOResponse;
import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.repository.FuncionarioRepository;

@Service
public class AuthService {

    private static final Duration DURACAO_SESSAO = Duration.ofHours(8);

    private final FuncionarioRepository funcionarioRepository;
    private final Map<String, SessaoUsuario> sessoes = new ConcurrentHashMap<>();

    public AuthService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public LoginDTOResponse login(LoginDTORequest request) {
        if (request == null || request.email() == null || request.senha() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o e-mail e a senha.");
        }

        Funcionario funcionario = funcionarioRepository.findByEmailIgnoreCase(request.email().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos."));

        if (!request.senha().equals(funcionario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
        }

        if (!isAdministrador(funcionario.getCargo())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este usuário não possui acesso administrativo.");
        }

        String token = UUID.randomUUID().toString();
        SessaoUsuario sessao = new SessaoUsuario(
                token,
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getCargo(),
                Instant.now().plus(DURACAO_SESSAO)
        );
        sessoes.put(token, sessao);

        return toResponse(sessao);
    }

    public LoginDTOResponse validar(String token) {
        SessaoUsuario sessao = sessoes.get(token);
        if (sessao == null || sessao.expiraEm().isBefore(Instant.now())) {
            if (sessao != null) sessoes.remove(token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida ou expirada.");
        }
        return toResponse(sessao);
    }

    public void logout(String token) {
        if (token != null) sessoes.remove(token);
    }

    private LoginDTOResponse toResponse(SessaoUsuario sessao) {
        return new LoginDTOResponse(
                sessao.token(),
                sessao.id(),
                sessao.nome(),
                sessao.email(),
                sessao.cargo(),
                true
        );
    }

    private boolean isAdministrador(String cargo) {
        if (cargo == null) return false;
        String normalizado = cargo.trim().toUpperCase();
        return normalizado.equals("ADMIN")
                || normalizado.equals("ADMINISTRADOR")
                || normalizado.equals("GERENTE");
    }
}
