package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Estoque;
import br.ufape.poo.mercado.repository.EstoqueRepository;

@Service
public class CadastroEstoque {

    @Autowired
    private EstoqueRepository colecaoEstoque;

    public Estoque salvarEstoque(Estoque entity) {
        return colecaoEstoque.save(entity);
    }

    public Estoque procurarEstoqueId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoEstoque.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Estoque não encontrado com o ID: " + id));
    }

    public List<Estoque> listarEstoques() {
        return colecaoEstoque.findAll();
    }

    public boolean verificarExistenciaEstoqueId(Integer id) {
        return colecaoEstoque.existsById(id);
    }

    public void removerEstoqueId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaEstoqueId(id)) {
            throw new ObjetoNaoEncontradoException("Estoque não encontrado com o ID: " + id);
        }
        colecaoEstoque.deleteById(id);
    }
}