package br.ufape.poo.mercado.comunicacao.dto.response;

import java.time.LocalDate;

public record LoteDTOResponse(
        Long id,
        String categoriaDoProduto,
        String marcaDoProduto,
        Integer quantidade,
        Integer codigo,
        Double valorTotalDoLote,
        LocalDate validade,
        LocalDate fabricacao
) {}