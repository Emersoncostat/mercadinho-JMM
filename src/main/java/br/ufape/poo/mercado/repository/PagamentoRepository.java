package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}