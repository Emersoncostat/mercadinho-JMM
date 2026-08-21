package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.EstoqueConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.EstoqueDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.EstoqueDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Estoque;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private EstoqueConversor conversor;

    @PostMapping
    public ResponseEntity<EstoqueDTOResponse> cadastrar(@RequestBody EstoqueDTORequest dto) {
        Estoque estoque = conversor.requestToEntity(dto);
        Estoque salvo = fachada.salvarEstoque(estoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueDTOResponse>> listarTodos() {
        List<Estoque> estoques = fachada.listarEstoques();
        List<EstoqueDTOResponse> response = estoques.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Estoque estoque = fachada.procurarEstoqueId(id);
        return ResponseEntity.ok(conversor.entityToResponse(estoque));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerEstoqueId(id);
        return ResponseEntity.noContent().build();
    }
}