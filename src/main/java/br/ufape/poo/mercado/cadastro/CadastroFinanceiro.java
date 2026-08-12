package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.repository.FinanceiroRepository;

@Service
public class CadastroFinanceiro implements InterfaceCadastroFinanceiro {

    @Autowired
    private FinanceiroRepository colecaoFinanceiro;

    @Override
    public Financeiro salvarFinanceiro(Financeiro entity) {
        return colecaoFinanceiro.save(entity);
    }

    @Override
    public Financeiro procurarFinanceiroId(Integer id) throws EntidadeNaoEncontradaException {
        Financeiro f = colecaoFinanceiro.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return f;
    }

    @Override
    public List<Financeiro> listarFinanceiros() {
        return colecaoFinanceiro.findAll();
    }

    @Override
    public boolean verificarExistenciaFinanceiroId(Integer id) {
        return colecaoFinanceiro.existsById(id);
    }

    @Override
    public void removerFinanceiroId(Integer id) throws EntidadeNaoEncontradaException {
        Financeiro f = colecaoFinanceiro.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoFinanceiro.deleteById(id);
    }
}
