package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroMercado {
    
    Mercado salvarMercado(Mercado entity);

    Mercado procurarMercadoId(Long id) throws EntidadeNaoEncontradaException;

    List<Mercado> listarMercados();

    boolean verificarExistenciaMercadoId(Long id);

    void removerMercadoId(Long id) throws EntidadeNaoEncontradaException;
}
