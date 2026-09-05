package br.ufape.poo.mercado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByEmailIgnoreCase(String email);
}
