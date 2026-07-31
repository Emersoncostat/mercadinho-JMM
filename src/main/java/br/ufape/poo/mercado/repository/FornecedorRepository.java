package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

}