package br.ufape.poo.mercado.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.repository.ProdutoRepository;

@Service
public class CadastroProduto implements InterfaceCadastroProduto {

    @Autowired
    private ProdutoRepository colecaoProduto;

    @Override
    public Produto salvarProduto(Produto entity) {
        return colecaoProduto.save(entity);
    }

    @Override
    public Produto procurarProdutoId(Integer id)
            throws EntidadeNaoEncontradaException {

        Produto p = colecaoProduto.findById(id).orElse(null);

        if (p == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        return p;
    }

    @Override
    public List<Produto> listarProdutos() {
        return colecaoProduto.findAll();
    }

    @Override
    public boolean verificarExistenciaProdutoId(Integer id) {
        return colecaoProduto.existsById(id);
    }

    @Override
    public void removerProdutoId(Integer id)
            throws EntidadeNaoEncontradaException {

        Produto p = colecaoProduto.findById(id).orElse(null);

        if (p == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        colecaoProduto.deleteById(id);
    }
}