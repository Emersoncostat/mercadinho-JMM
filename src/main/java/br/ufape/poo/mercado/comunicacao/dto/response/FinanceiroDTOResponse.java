package br.ufape.poo.mercado.comunicacao.dto.response;

public record FinanceiroDTOResponse(
        Integer id,
        Double receita,
        Double despesa,
        Double lucro,
        String dataRegistro
) {}