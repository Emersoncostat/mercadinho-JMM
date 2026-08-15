package br.ufape.poo.mercado.comunicacao.dto.request;



public record FornecedorDTORequest (

        Integer id,
        String nome,
        String cnpj,
        String telefone,
        String endereco,
        String email
){}
