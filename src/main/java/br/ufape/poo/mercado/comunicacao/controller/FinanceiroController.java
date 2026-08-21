package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.FinanceiroConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.FinanceiroDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.FinanceiroDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/financeiros")
public class FinanceiroController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private FinanceiroConversor conversor;

    @PostMapping
    public ResponseEntity<FinanceiroDTOResponse> cadastrar(@RequestBody FinanceiroDTORequest dto) {
        Financeiro financeiro = conversor.requestToEntity(dto);
        Financeiro salvo = fachada.salvarFinanceiro(financeiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<FinanceiroDTOResponse>> listarTodos() {
        List<Financeiro> financeiros = fachada.listarFinanceiros();
        List<FinanceiroDTOResponse> response = financeiros.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceiroDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Financeiro financeiro = fachada.procurarFinanceiroId(id);
        return ResponseEntity.ok(conversor.entityToResponse(financeiro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerFinanceiroId(id);
        return ResponseEntity.noContent().build();
    }
}