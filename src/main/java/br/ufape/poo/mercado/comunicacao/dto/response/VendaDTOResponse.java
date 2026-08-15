package br.ufape.poo.mercado.comunicacao.dto.response;

public record VendaDTOResponse(
        Integer id,
        String dataVenda,
        Double valorTotal,
        Integer quantidadeProdutos,
        Double desconto
) {}