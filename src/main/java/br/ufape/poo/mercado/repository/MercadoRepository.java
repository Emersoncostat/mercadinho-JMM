package br.ufape.poo.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ufape.poo.mercado.model.Mercado;

@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Long> {
}