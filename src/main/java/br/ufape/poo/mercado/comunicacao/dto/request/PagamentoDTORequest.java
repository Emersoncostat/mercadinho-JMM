package br.ufape.poo.mercado.comunicacao.dto.request;

public record PagamentoDTORequest(
        String tipoPagamento,
        Double valorPago,
        String dataPagamento,
        String status,
        Double troco
) {}