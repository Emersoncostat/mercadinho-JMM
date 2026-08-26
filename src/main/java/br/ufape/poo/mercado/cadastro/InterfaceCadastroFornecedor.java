package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface  InterfaceCadastroFornecedor {

    Fornecedor salvarFornecedor(Fornecedor entity);

    Fornecedor procurarFornecedorId(Integer id) throws EntidadeNaoEncontradaException;

    Fornecedor atualizar(Integer id, Fornecedor fornecedor) throws EntidadeNaoEncontradaException;

    List<Fornecedor> listarFornecedores();

    boolean verificarExistenciaFornecedorId(Integer id);

    void removerFornecedorId(Integer id) throws EntidadeNaoEncontradaException;
}
