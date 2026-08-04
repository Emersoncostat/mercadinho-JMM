package br.ufape.poo.mercado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Caixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Double saldoInicial;
	private Double saldoFinal;
	private String dataAbertura;
	private String dataFechamento;

	public Caixa() {} //construtor

	//get e set
	public Integer getId() { return id; }
	public void setId(Integer id) {this.id = id;}

	public double getSaldoInicial() {return saldoInicial; }
	public void setSaldoInicial (double saldoInicial) { this.saldoInicial = saldoInicial;}

	public double getSaldoFinal() {return saldoFinal; }
	public void setSaldoFinal (double saldoFinal) { this.saldoFinal = saldoFinal; }


	//função
	public void abrirCaixa (double valorInicial) {
		this.saldoInicial = valorInicial;
		this.saldoFinal = valorInicial;
	}

	public void fecharCaixa() {
		//Decidir se o mercado vai ser digital também ou apenas fisico.

	}

	public double calcularSaldo(double totalVendas) {
		if (this.saldoInicial != null) {
			this.saldoFinal = this.saldoInicial + totalVendas;
		}else {
			this.saldoFinal = totalVendas;
		}
		return this.saldoFinal;
	}

}