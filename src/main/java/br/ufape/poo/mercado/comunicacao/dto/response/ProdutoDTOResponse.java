package br.ufape.poo.mercado.comunicacao.dto.response;

import java.sql.Date;

public record ProdutoDTOResponse(
        Integer codigo,
        String nome,
        String descricao,
        String marca,
        Double preco,
        String codigoBarras,
        Date validade
) {}