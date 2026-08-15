package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.FornecedorConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.FornecedorDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.FornecedorDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private FornecedorConversor conversor;

    @PostMapping
    public ResponseEntity<FornecedorDTOResponse> cadastrar(@RequestBody FornecedorDTORequest dto) {
        Fornecedor fornecedor = conversor.requestToEntity(dto);
        Fornecedor salvo = fachada.salvarFornecedor(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTOResponse>> listarTodos() {
        List<Fornecedor> fornecedores = fachada.listarFornecedores();
        List<FornecedorDTOResponse> response = fornecedores.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Fornecedor fornecedor = fachada.procurarFornecedorId(id);
            return ResponseEntity.ok(conversor.entityToResponse(fornecedor));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            fachada.removerFornecedorId(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}