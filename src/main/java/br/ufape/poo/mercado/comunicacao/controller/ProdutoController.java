package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.ProdutoConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.ProdutoDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.ProdutoDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private ProdutoConversor conversor;

    @PostMapping
    public ResponseEntity<ProdutoDTOResponse> cadastrar(@RequestBody ProdutoDTORequest dto) {
        Produto produto = conversor.requestToEntity(dto);
        Produto salvo = fachada.salvarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTOResponse>> listarTodos() {
        List<Produto> produtos = fachada.listarProdutos();
        List<ProdutoDTOResponse> response = produtos.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoDTOResponse> buscarPorCodigo(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Produto produto = fachada.procurarProdutoId(id);
        return ResponseEntity.ok(conversor.entityToResponse(produto));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerProdutoId(id);
        return ResponseEntity.noContent().build();
    }
}