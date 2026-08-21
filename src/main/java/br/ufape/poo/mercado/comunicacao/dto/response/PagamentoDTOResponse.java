package br.ufape.poo.mercado.comunicacao.dto.response;

public record PagamentoDTOResponse(
        Integer id,
        String tipoPagamento,
        Double valorPago,
        String dataPagamento,
        String status,
        Double troco
) {}