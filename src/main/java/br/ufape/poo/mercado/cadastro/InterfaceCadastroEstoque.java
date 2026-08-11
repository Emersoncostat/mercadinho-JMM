package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Estoque;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroEstoque {
    
    Estoque salvarEstoque(Estoque entity);

    Estoque procurarEstoqueId(Integer id) throws EntidadeNaoEncontradaException;

    List<Estoque> listarEstoques();

    boolean verificarExistenciaEstoqueId(Integer id);

    void removerEstoqueId(Integer id) throws EntidadeNaoEncontradaException;
}
