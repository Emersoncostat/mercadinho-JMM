package br.ufape.poo.mercado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import jakarta.persistence.Id;

@Entity
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoriaDoProduto;
    private String marcaDoProduto;
    private Integer quantidade;
    private Integer codigo;
    private Double valorTotalDoLote;
    private LocalDate validade;
    private LocalDate fabricacao;


    public Lote() {
    }

    // Construtor com atributos
    public Lote(Long id, String categoriaDoProduto, String marcaDoProduto, Integer quantidade, Integer codigo, Double valorTotalDoLote, LocalDate validade, LocalDate fabricacao) {
        this.id = id;
        this.categoriaDoProduto = categoriaDoProduto;
        this.marcaDoProduto = marcaDoProduto;
        this.quantidade = quantidade;
        this.codigo = codigo;
        this.valorTotalDoLote = valorTotalDoLote;
        this.validade = validade;
        this.fabricacao = fabricacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoriaDoProduto() {
        return categoriaDoProduto;
    }

    public void setCategoriaDoProduto(String categoriaDoProduto) {
        this.categoriaDoProduto = categoriaDoProduto;
    }

    public String getMarcaDoProduto() {
        return marcaDoProduto;
    }

    public void setMarcaDoProduto(String marcaDoProduto) {
        this.marcaDoProduto = marcaDoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getValorTotalDoLote() {
        return valorTotalDoLote;
    }

    public void setValorTotalDoLote(Double valorTotalDoLote) {
        this.valorTotalDoLote = valorTotalDoLote;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public LocalDate getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(LocalDate fabricacao) {
        this.fabricacao = fabricacao;
    }
}