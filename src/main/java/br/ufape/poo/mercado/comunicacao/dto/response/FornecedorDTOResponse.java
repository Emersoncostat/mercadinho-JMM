package br.ufape.poo.mercado.comunicacao.dto.response;

public record FornecedorDTOResponse(

        Integer id,
        String nome,
        String cnpj,
        String telefone,
        String endereco,
        String email
) {}
