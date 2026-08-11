package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.repository.FinanceiroRepository;

@Service
public class CadastroFinanceiro {

    @Autowired
    private FinanceiroRepository colecaoFinanceiro;

    public Financeiro salvarFinanceiro(Financeiro entity) {
        return colecaoFinanceiro.save(entity);
    }

    public Financeiro procurarFinanceiroId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoFinanceiro.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Registro Financeiro não encontrado com o ID: " + id));
    }

    public List<Financeiro> listarFinanceiros() {
        return colecaoFinanceiro.findAll();
    }

    public boolean verificarExistenciaFinanceiroId(Integer id) {
        return colecaoFinanceiro.existsById(id);
    }

    public void removerFinanceiroId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaFinanceiroId(id)) {
            throw new ObjetoNaoEncontradoException("Registro Financeiro não encontrado com o ID: " + id);
        }
        colecaoFinanceiro.deleteById(id);
    }
}