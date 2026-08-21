package br.ufape.poo.mercado.comunicacao.dto.response;

public record FuncionarioDTOResponse(
        Integer id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String cargo,
        Double salario
) {}