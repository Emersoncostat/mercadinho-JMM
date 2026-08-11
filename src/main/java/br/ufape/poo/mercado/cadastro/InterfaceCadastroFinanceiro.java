package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroFinanceiro {
    
    Financeiro cadastrarFinanceiro(Financeiro entity);

    Financeiro procurarFinanceiroId(Integer id) throws EntidadeNaoEncontradaException;

    List<Financeiro> lisrtFinanceiros();

    boolean verificarExistenciaFinanceiroId(Integer id);

    void removerFinanceiroId(Integer id) throws EntidadeNaoEncontradaException;
}
