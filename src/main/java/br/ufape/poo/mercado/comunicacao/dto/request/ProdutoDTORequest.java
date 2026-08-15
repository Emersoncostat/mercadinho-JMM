package br.ufape.poo.mercado.comunicacao.dto.request;

import java.sql.Date;

public record ProdutoDTORequest(
        String nome,
        String descricao,
        String marca,
        Double preco,
        String codigoBarras,
        Date validade
) {}