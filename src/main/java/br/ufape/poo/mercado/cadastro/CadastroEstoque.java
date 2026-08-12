package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Estoque;
import br.ufape.poo.mercado.repository.EstoqueRepository;

@Service
public class CadastroEstoque implements InterfaceCadastroEstoque {
    @Autowired
    private EstoqueRepository colecaoEstoque;

    @Override
    public Estoque salvarEstoque(Estoque entity) {
        return colecaoEstoque.save(entity);
    }
    @Override
    public Estoque procurarEstoqueId(Integer id) throws EntidadeNaoEncontradaException {
        return colecaoEstoque.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.valueOf(id)));
    }
    @Override
    public List<Estoque> listarEstoques() {
        return colecaoEstoque.findAll();
    }
    @Override
    public boolean verificarExistenciaEstoqueId(Integer id) {
        return colecaoEstoque.existsById(id);
    }
    @Override
    public void removerEstoqueId(Integer id) throws EntidadeNaoEncontradaException {
        if (!verificarExistenciaEstoqueId(id)) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoEstoque.deleteById(id);
    }
}