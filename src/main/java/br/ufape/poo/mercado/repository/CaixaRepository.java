package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Caixa;

public interface CaixaRepository extends JpaRepository<Caixa, Integer> {

}