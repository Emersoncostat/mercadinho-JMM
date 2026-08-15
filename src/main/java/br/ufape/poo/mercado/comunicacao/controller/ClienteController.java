package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.ClienteConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.ClienteDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.ClienteDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Cliente;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private ClienteConversor conversor;

    @PostMapping
    public ResponseEntity<ClienteDTOResponse> cadastrar(@RequestBody ClienteDTORequest dto) {
        Cliente cliente = conversor.requestToEntity(dto);
        Cliente salvo = fachada.salvarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTOResponse>> listarTodos() {
        List<Cliente> clientes = fachada.listarClientes();
        List<ClienteDTOResponse> response = clientes.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Cliente cliente = fachada.procurarClienteId(id);
        return ResponseEntity.ok(conversor.entityToResponse(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerClienteId(id);
        return ResponseEntity.noContent().build();
    }
}