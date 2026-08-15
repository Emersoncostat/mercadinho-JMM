package br.ufape.poo.mercado.comunicacao.dto.request;

public record VendaDTORequest(
        Integer idProduto,
        Integer quantidade,
        Double desconto
) {}