package br.ufape.poo.mercado.comunicacao.dto.response;

public record CaixaDTOResponse(
        Integer id,
        Double saldoInicial,
        Double saldoFinal,
        String dataAbertura,
        String dataFechamento
) {}
