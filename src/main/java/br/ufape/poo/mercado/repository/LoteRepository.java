package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Lote;

public interface LoteRepository extends JpaRepository<Lote, Integer> {

}