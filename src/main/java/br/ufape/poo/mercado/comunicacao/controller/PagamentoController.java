package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.PagamentoConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.PagamentoDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.PagamentoDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Pagamento;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private PagamentoConversor conversor;

    @PostMapping
    public ResponseEntity<PagamentoDTOResponse> cadastrar(@RequestBody PagamentoDTORequest dto) {
        Pagamento pagamento = conversor.requestToEntity(dto);
        Pagamento salvo = fachada.salvarPagamento(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTOResponse>> listarTodos() {
        List<Pagamento> pagamentos = fachada.listarPagamentos();
        List<PagamentoDTOResponse> response = pagamentos.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Pagamento pagamento = fachada.procurarPagamentoId(id);
        return ResponseEntity.ok(conversor.entityToResponse(pagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerPagamentoId(id);
        return ResponseEntity.noContent().build();
    }
}