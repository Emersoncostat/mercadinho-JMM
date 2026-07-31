package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

}