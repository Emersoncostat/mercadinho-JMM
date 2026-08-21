package br.ufape.poo.mercado.comunicacao.dto.request;

public record MercadoDTORequest(
        String nome,
        String cnpj,
        String endereco,
        String telefone,
        String email,
        String horarioFuncionamento
) {}