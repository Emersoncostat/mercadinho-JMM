package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.repository.ProdutoRepository;

@Service
public class CadastroProduto {

    @Autowired
    private ProdutoRepository colecaoProduto;

    public Produto salvarProduto(Produto entity) {
        return colecaoProduto.save(entity);
    }

    public Produto procurarProdutoId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoProduto.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Produto não encontrado com o ID: " + id));
    }

    public List<Produto> listarProdutos() {
        return colecaoProduto.findAll();
    }

    public boolean verificarExistenciaProdutoId(Integer id) {
        return colecaoProduto.existsById(id);
    }

    public void removerProdutoId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaProdutoId(id)) {
            throw new ObjetoNaoEncontradoException("Produto não encontrado com o ID: " + id);
        }
        colecaoProduto.deleteById(id);
    }
}