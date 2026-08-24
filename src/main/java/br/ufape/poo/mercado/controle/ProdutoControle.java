package br.ufape.poo.mercado.controle;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.model.Produto; 
import br.ufape.poo.mercado.repository.ProdutoRepository; 

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produtos")
public class ProdutoControle {

    @Autowired
    private ProdutoRepository repository; 

    
    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

   
    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return repository.save(produto);
    }
}