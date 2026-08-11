package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroVenda {
    
    Venda salvarVenda(Venda entity);

    Venda procurarVendaId(Integer id) throws EntidadeNaoEncontradaException;

    List<Venda> listarVendas();

    boolean verificarExistenciaVendaId(Integer id);

    void removerVendaId(Integer id) throws EntidadeNaoEncontradaException;
}
