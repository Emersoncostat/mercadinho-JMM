package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroLote {
    
    Lote salvarLote(Lote entity);

    Lote procurarLoteId(Long id) throws EntidadeNaoEncontradaException;

    List<Lote> listarLotes();

    boolean verificarExistenciaLoteId(Long id);

    void removerLoteId(Long id) throws EntidadeNaoEncontradaException;
}
