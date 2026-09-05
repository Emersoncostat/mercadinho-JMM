package br.ufape.poo.mercado.comunicacao.dto.response;

public record LoginDTOResponse(
        String token,
        Integer id,
        String nome,
        String email,
        String cargo,
        boolean administrador
) {}
