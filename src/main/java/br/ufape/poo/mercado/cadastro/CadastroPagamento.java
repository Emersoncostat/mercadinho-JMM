package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Pagamento;
import br.ufape.poo.mercado.repository.PagamentoRepository;

@Service
public class CadastroPagamento {

    @Autowired
    private PagamentoRepository colecaoPagamento;

    public Pagamento salvarPagamento(Pagamento entity) {
        return colecaoPagamento.save(entity);
    }

    public Pagamento procurarPagamentoId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoPagamento.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Pagamento não encontrado com o ID: " + id));
    }

    public List<Pagamento> listarPagamentos() {
        return colecaoPagamento.findAll();
    }

    public boolean verificarExistenciaPagamentoId(Integer id) {
        return colecaoPagamento.existsById(id);
    }

    public void removerPagamentoId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaPagamentoId(id)) {
            throw new ObjetoNaoEncontradoException("Pagamento não encontrado com o ID: " + id);
        }
        colecaoPagamento.deleteById(id);
    }
}