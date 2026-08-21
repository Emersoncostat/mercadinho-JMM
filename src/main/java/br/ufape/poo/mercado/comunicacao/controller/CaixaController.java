package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.CaixaConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.CaixaDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.CaixaDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Caixa;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/caixas")
public class CaixaController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private CaixaConversor conversor;

    @PostMapping
    public ResponseEntity<CaixaDTOResponse> cadastrar(@RequestBody CaixaDTORequest dto) {
        Caixa caixa = conversor.requestToEntity(dto);
        Caixa salvo = fachada.salvarCaixa(caixa);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<CaixaDTOResponse>> listarTodos() {
        List<Caixa> caixas = fachada.listarCaixas();
        List<CaixaDTOResponse> response = caixas.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaixaDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Caixa caixa = fachada.procurarCaixaId(id);
        return ResponseEntity.ok(conversor.entityToResponse(caixa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerCaixaId(id);
        return ResponseEntity.noContent().build();
    }
}