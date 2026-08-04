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

    public Estoque() {} // Construtor sem argumentos

    // Construtor 
    public Estoque(Integer quantidadeDisponivel, Integer estoqueMinimo, Integer estoqueMaximo, String dataAtualizacao) {
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueMaximo = estoqueMaximo;
        this.dataAtualizacao = dataAtualizacao;
    }

    //get e set
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Integer getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public void setEstoqueMaximo(Integer estoqueMaximo) {
        this.estoqueMaximo = estoqueMaximo;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }


    //função
    public void adicionarProduto(int qtd) {
        if (qtd > 0) {
            if (this.quantidadeDisponivel == null) {
                this.quantidadeDisponivel = 0;
            }
            this.quantidadeDisponivel += qtd;
        }
    }

    public void retirarProduto(int qtd) {
        if (qtd > 0 && this.quantidadeDisponivel != null && this.quantidadeDisponivel >= qtd) {
            this.quantidadeDisponivel -= qtd;
        }
    }

    public void atualizarQuantidade(int novaQtd) {
        if (novaQtd >= 0) {
            this.quantidadeDisponivel = novaQtd;
        }
    }
}