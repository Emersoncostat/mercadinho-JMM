package br.ufape.poo.mercado.cadastro;

import java.util.List;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroFinanceiro {
    Financeiro salvarFinanceiro(Financeiro financeiro);
    Financeiro procurarFinanceiroId(Integer id) throws EntidadeNaoEncontradaException;
    List<Financeiro> listarFinanceiros();
    boolean verificarExistenciaFinanceiroId(Integer id);
    void removerFinanceiroId(Integer id) throws EntidadeNaoEncontradaException;
}