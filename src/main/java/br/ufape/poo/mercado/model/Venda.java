package br.ufape.poo.mercado.model;

import jakarta.persistence.*;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dataVenda;
    private Double valorTotal;
    private Integer quantidadeProdutos;
    private Double desconto;

    @ManyToOne
    private Produto produto;

    public Venda() {}
    // Construtor
    public Venda(String dataVenda, Double valorTotal, Integer quantidadeProdutos, Double desconto, Produto produto) {
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.quantidadeProdutos = quantidadeProdutos;
        this.desconto = desconto;
        this.produto = produto;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public void setQuantidadeProdutos(Integer quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    // Função para adicionar produtos à venda
    public void adicionarProduto (Produto p, Integer quantidade) {
        Double subtotal = p.getPreco() * quantidade;
        this.valorTotal += subtotal;
        this.quantidadeProdutos += quantidade;
    }

    // Função para finalizar a venda
    public void finalizarVenda() {
        this.valorTotal -= this.valorTotal * (this.desconto / 100);
    }
}