package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.repository.FinanceiroRepository;

@Service
public class CadastroFinanceiro {

    @Autowired
    private FinanceiroRepository colecaoFinanceiro;

    public Financeiro salvarFinanceiro(Financeiro entity) {
        return colecaoFinanceiro.save(entity);
    }
    public Financeiro procurarFinanceiroId(Integer id) throws EntidadeNaoEncontradaException {
        Financeiro f = colecaoFinanceiro.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return f;
    }
    public List<Financeiro> listarFinanceiros() {
        return colecaoFinanceiro.findAll();
    }
    public boolean verificarExistenciaFinanceiroId(Integer id) {
        return colecaoFinanceiro.existsById(id);
    }
    public void removerFinanceiroId(Integer id) throws EntidadeNaoEncontradaException {
        Financeiro f = colecaoFinanceiro.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoFinanceiro.deleteById(id);
    }
}