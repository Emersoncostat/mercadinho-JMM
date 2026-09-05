package br.ufape.poo.mercado.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.repository.FuncionarioRepository;

@Component
@Profile("!test")
public class AdminBootstrap implements CommandLineRunner {

    private final FuncionarioRepository funcionarioRepository;

    @Value("${app.admin.nome}")
    private String nome;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String senha;

    public AdminBootstrap(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public void run(String... args) {
        if (funcionarioRepository.findByEmailIgnoreCase(email).isPresent()) return;

        Funcionario administrador = new Funcionario();
        administrador.setNome(nome);
        administrador.setCpf("000.000.000-00");
        administrador.setTelefone("");
        administrador.setEmail(email);
        administrador.setCargo("Administrador");
        administrador.setSalario(0.0);
        administrador.setSenha(senha);
        funcionarioRepository.save(administrador);
    }
}
