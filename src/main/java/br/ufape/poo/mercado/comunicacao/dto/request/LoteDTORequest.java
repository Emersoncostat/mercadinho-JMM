package br.ufape.poo.mercado.comunicacao.dto.request;

import java.time.LocalDate;

public record LoteDTORequest(
        String categoriaDoProduto,
        String marcaDoProduto,
        Integer quantidade,
        Integer codigo,
        Double valorTotalDoLote,
        LocalDate validade,
        LocalDate fabricacao
) {}