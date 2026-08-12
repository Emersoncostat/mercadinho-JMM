package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Pagamento;
import br.ufape.poo.mercado.repository.PagamentoRepository;

@Service
public class CadastroPagamento implements InterfaceCadastroPagamento {
    @Autowired
    private PagamentoRepository colecaoPagamento;

    public Pagamento salvarPagamento(Pagamento entity) {
        return colecaoPagamento.save(entity);
    }

    public Pagamento procurarPagamentoId(Integer id) throws EntidadeNaoEncontradaException {
        Pagamento p = colecaoPagamento.findById(id).orElse(null);
        if (p == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return p;
    }

    public List<Pagamento> listarPagamentos() {
        return colecaoPagamento.findAll();
    }

    public boolean verificarExistenciaPagamentoId(Integer id) {
        return colecaoPagamento.existsById(id);
    }

    public void removerPagamentoId(Integer id) throws EntidadeNaoEncontradaException {
        Pagamento p = colecaoPagamento.findById(id).orElse(null);
        if (p == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoPagamento.deleteById(id);
    }
}