package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroProduto {
    
    Produto salvarProduto(Produto entity);

    Produto procurarProdutoId(Integer id) throws EntidadeNaoEncontradaException;

    List<Produto> listarProdutos();

    boolean verificarExistenciaProdutoId(Integer id);

    void removerProdutoId(Integer id) throws EntidadeNaoEncontradaException;

    Produto atualizar(Integer id, Produto produto) throws EntidadeNaoEncontradaException;
}
