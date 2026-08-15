package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.VendaConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.VendaDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.VendaDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private VendaConversor conversor;

    @PostMapping
    public ResponseEntity<VendaDTOResponse> realizarVenda(@RequestBody VendaDTORequest dto) throws EntidadeNaoEncontradaException {
        Venda venda = fachada.realizarVendaProduto(
                dto.idProduto(),
                dto.quantidade(),
                dto.desconto()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(venda));
    }

    @GetMapping
    public ResponseEntity<List<VendaDTOResponse>> listarTodas() {
        List<Venda> vendas = fachada.listarVendas();
        List<VendaDTOResponse> response = vendas.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Venda venda = fachada.procurarVendaId(id);
        return ResponseEntity.ok(conversor.entityToResponse(venda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerVendaId(id);
        return ResponseEntity.noContent().build();
    }
}