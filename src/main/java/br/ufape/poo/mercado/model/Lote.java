package br.ufape.poo.mercado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoriaDoProduto;
    private String marcaDoProduto;
    private Integer quantidade;
    private Integer codigo;
    private Double valorTotalDoLote;
    private String validade;
    private String fabricacao;

}
