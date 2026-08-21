package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.LoteConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.LoteDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.LoteDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private LoteConversor conversor;

    @PostMapping
    public ResponseEntity<LoteDTOResponse> cadastrar(@RequestBody LoteDTORequest dto) {
        Lote lote = conversor.requestToEntity(dto);
        Lote salvo = fachada.salvarLote(lote);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<LoteDTOResponse>> listarTodos() {
        List<Lote> lotes = fachada.listarLotes();
        List<LoteDTOResponse> response = lotes.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteDTOResponse> buscarPorId(@PathVariable Long id) throws EntidadeNaoEncontradaException {
        Lote lote = fachada.procurarLoteId(id);
        return ResponseEntity.ok(conversor.entityToResponse(lote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EntidadeNaoEncontradaException {
        fachada.removerLoteId(id);
        return ResponseEntity.noContent().build();
    }
}