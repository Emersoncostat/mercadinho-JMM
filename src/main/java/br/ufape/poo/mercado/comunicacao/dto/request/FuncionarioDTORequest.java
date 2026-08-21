package br.ufape.poo.mercado.comunicacao.dto.request;

public record FuncionarioDTORequest(
        String nome,
        String cpf,
        String telefone,
        String email,
        String cargo,
        Double salario,
        String senha
) {}