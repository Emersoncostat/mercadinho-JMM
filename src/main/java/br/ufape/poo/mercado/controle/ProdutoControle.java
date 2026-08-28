package br.ufape.poo.mercado.controle;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.cadastro.CadastroProduto;
import br.ufape.poo.mercado.model.Produto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produtos")
public class ProdutoControle {

    @Autowired
    private CadastroProduto cadastroProduto;

    @GetMapping
    public List<Produto> listar() {
        return cadastroProduto.listarProdutos();
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return cadastroProduto.salvarProduto(produto);
    }
}