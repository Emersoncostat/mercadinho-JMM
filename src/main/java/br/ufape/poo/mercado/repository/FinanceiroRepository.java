package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Financeiro;

public interface FinanceiroRepository extends JpaRepository<Financeiro, Integer> {

}
