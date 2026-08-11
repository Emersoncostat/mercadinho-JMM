package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Caixa;
import br.ufape.poo.mercado.repository.CaixaRepository;

@Service
public class CadastroCaixa {

    @Autowired
    private CaixaRepository colecaoCaixa;

    public Caixa salvarCaixa(Caixa entity) {
        return colecaoCaixa.save(entity);
    }

    public Caixa procurarCaixaId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoCaixa.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Caixa não encontrado com o ID: " + id));
    }

    public List<Caixa> listarCaixas() {
        return colecaoCaixa.findAll();
    }

    public boolean verificarExistenciaCaixaId(Integer id) {
        return colecaoCaixa.existsById(id);
    }

    public void removerCaixaId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaCaixaId(id)) {
            throw new ObjetoNaoEncontradoException("Caixa não encontrado com o ID: " + id);
        }
        colecaoCaixa.deleteById(id);
    }
}