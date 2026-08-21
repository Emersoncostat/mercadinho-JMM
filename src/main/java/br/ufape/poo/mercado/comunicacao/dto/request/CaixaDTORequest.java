package br.ufape.poo.mercado.comunicacao.dto.request;

public record CaixaDTORequest(
        Double saldoInicial,
        Double saldoFinal,
        String dataAbertura,
        String dataFechamento
) {}