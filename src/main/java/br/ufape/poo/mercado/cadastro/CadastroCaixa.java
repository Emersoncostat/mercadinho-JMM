package br.ufape.poo.mercado.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.model.Caixa;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.repository.CaixaRepository;

@Service
public class CadastroCaixa implements InterfaceCadastroCaixa {

    @Autowired
    private CaixaRepository colecaoCaixa;

    @Override
    public Caixa salvarCaixa(Caixa entity) {
        return colecaoCaixa.save(entity);
    }

    @Override
    public Caixa procurarCaixaId(Integer id)
            throws EntidadeNaoEncontradaException {

        return colecaoCaixa.findById(id)
                .orElseThrow(
                    () -> new EntidadeNaoEncontradaException(
                        String.valueOf(id)
                    )
                );
    }

    @Override
    public List<Caixa> listarCaixas() {
        return colecaoCaixa.findAll();
    }

    @Override
    public boolean verificarExistenciaCaixaId(Integer id) {
        return colecaoCaixa.existsById(id);
    }

    @Override
    public void removerCaixaId(Integer id)
            throws EntidadeNaoEncontradaException {

        if (!verificarExistenciaCaixaId(id)) {
            throw new EntidadeNaoEncontradaException(
                String.valueOf(id)
            );
        }

        colecaoCaixa.deleteById(id);
    }
}