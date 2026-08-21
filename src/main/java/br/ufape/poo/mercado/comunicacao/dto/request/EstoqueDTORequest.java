package br.ufape.poo.mercado.comunicacao.dto.request;

public record EstoqueDTORequest(
        Integer quantidadeDisponivel,
        Integer estoqueMinimo,
        Integer estoqueMaximo,
        String dataAtualizacao
) {}