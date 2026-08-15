package br.ufape.poo.mercado.comunicacao.dto.response;

public record ClienteDTOResponse(
        Integer id,
        String nome,
        String cpf,
        String telefone,
        String endereco,
        String email
) {}