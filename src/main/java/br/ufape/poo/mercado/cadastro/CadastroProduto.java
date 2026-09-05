package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.repository.ProdutoRepository;

@Service
public class CadastroProduto implements InterfaceCadastroProduto {
    @Autowired
    private ProdutoRepository colecaoProduto;

    public Produto salvarProduto(Produto entity) {
        return colecaoProduto.save(entity);
    }

    public Produto procurarProdutoId(Integer id) throws EntidadeNaoEncontradaException {
        Produto p = colecaoProduto.findById(id).orElse(null);
        if (p == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return p;
    }

    @Override
    public Produto atualizar(Integer id, Produto produto) throws EntidadeNaoEncontradaException {

        Produto produtoExistente = procurarProdutoId(id);

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setMarca(produto.getMarca());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setCodigoBarras(produto.getCodigoBarras());
        produtoExistente.setValidade(produto.getValidade());

        return colecaoProduto.save(produtoExistente);
    }

    public List<Produto> listarProdutos() {
        return colecaoProduto.findAll();
    }

    public boolean verificarExistenciaProdutoId(Integer id) {
        return colecaoProduto.existsById(id);
    }

    public void removerProdutoId(Integer id) throws EntidadeNaoEncontradaException {
        Produto p = colecaoProduto.findById(id).orElse(null);
        if (p == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoProduto.deleteById(id);
    }
}
