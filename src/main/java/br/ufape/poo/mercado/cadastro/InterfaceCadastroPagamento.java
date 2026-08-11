package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Pagamento;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface  InterfaceCadastroPagamento {
    
    Pagamento salvarPagamento(Pagamento entity);

    Pagamento procurarPagamentoId(Integer id) throws EntidadeNaoEncontradaException;

    List<Pagamento> listarPagamentos();

    boolean verificarExistenciaPagamentoId(Integer id);

    void removerPagamentoId(Integer id) throws EntidadeNaoEncontradaException;
}
