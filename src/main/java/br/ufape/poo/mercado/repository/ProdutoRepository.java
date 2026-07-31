package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}