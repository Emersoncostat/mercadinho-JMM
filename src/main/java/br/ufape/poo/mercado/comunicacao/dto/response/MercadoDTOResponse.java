package br.ufape.poo.mercado.comunicacao.dto.response;

public record MercadoDTOResponse(
        Long id,
        String nome,
        String cnpj,
        String endereco,
        String telefone,
        String email,
        String horarioFuncionamento
) {}