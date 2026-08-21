package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.MercadoConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.MercadoDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.MercadoDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/mercados")
public class MercadoController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private MercadoConversor conversor;

    @PostMapping
    public ResponseEntity<MercadoDTOResponse> cadastrar(@RequestBody MercadoDTORequest dto) {
        Mercado mercado = conversor.requestToEntity(dto);
        Mercado salvo = fachada.salvarMercado(mercado);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<MercadoDTOResponse>> listarTodos() {
        List<Mercado> mercados = fachada.listarMercados();
        List<MercadoDTOResponse> response = mercados.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MercadoDTOResponse> buscarPorId(@PathVariable Long id) throws EntidadeNaoEncontradaException {
        Mercado mercado = fachada.procurarMercadoId(id);
        return ResponseEntity.ok(conversor.entityToResponse(mercado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EntidadeNaoEncontradaException {
        fachada.removerMercadoId(id);
        return ResponseEntity.noContent().build();
    }
}