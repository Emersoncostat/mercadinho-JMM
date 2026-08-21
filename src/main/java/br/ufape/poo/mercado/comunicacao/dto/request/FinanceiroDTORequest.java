package br.ufape.poo.mercado.comunicacao.dto.request;

public record FinanceiroDTORequest(
        Double receita,
        Double despesa,
        Double lucro,
        String dataRegistro
) {}