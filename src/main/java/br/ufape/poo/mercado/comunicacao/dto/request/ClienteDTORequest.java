package br.ufape.poo.mercado.comunicacao.dto.request;



public record ClienteDTORequest(

        String nome,
        String cpf,
        String telefone,
        String endereco,
        String email
) {}