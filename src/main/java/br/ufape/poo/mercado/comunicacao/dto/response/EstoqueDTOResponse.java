package br.ufape.poo.mercado.comunicacao.dto.response;

public record EstoqueDTOResponse(
        Integer id,
        Integer quantidadeDisponivel,
        Integer estoqueMinimo,
        Integer estoqueMaximo,
        String dataAtualizacao
) {}