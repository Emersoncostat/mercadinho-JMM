package br.ufape.poo.mercado.auth;

import java.time.Instant;

public record SessaoUsuario(
        String token,
        Integer id,
        String nome,
        String email,
        String cargo,
        Instant expiraEm
) {}
