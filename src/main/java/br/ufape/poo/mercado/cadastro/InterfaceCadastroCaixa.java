package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Caixa;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroCaixa {

    Caixa salvarCaixa(Caixa entity);

    Caixa procurarCaixaId(Integer id) throws EntidadeNaoEncontradaException;

    List<Caixa> listarCaixas();

    boolean verificarExistenciaCaixaId(Integer id);

    void removerCaixaId(Integer id) throws EntidadeNaoEncontradaException;
}
