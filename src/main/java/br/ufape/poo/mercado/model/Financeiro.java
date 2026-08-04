package br.ufape.poo.mercado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double receita;
    private Double despesa;
    private Double lucro;
    private String dataRegistro;

    public Financeiro() {} // Construtor sem argumentos

    //construtor
    public Financeiro(Double receita, Double despesa, Double lucro, String dataRegistro) {
        this.receita = receita;
        this.despesa = despesa;
        this.lucro = lucro;
        this.dataRegistro = dataRegistro;
    }

    //get e set

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getReceita() {
        return receita;
    }

    public void setReceita(Double receita) {
        this.receita = receita;
    }

    public Double getDespesa() {
        return despesa;
    }

    public void setDespesa(Double despesa) {
        this.despesa = despesa;
    }

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    //função
    
    public Double calcularLucro() {
        double rec = (this.receita != null) ? this.receita : 0.0;
        double desp = (this.despesa != null) ? this.despesa : 0.0;
        
        this.lucro = rec - desp;
        return this.lucro;
    }
}
