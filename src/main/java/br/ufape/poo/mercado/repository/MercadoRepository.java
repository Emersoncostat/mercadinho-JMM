package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufape.poo.mercado.model.Mercado;

public interface MercadoRepository extends JpaRepository<Mercado, Integer> {

}
