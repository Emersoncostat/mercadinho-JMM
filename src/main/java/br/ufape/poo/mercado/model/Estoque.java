package br.ufape.poo.mercado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantidadeDisponivel;
    private Integer estoqueMinimo;
    private Integer estoqueMaximo;
    private String dataAtualizacao;

}