package br.ufape.poo.mercado.comunicacao.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufape.poo.mercado.comunicacao.conversor.FuncionarioConversor;
import br.ufape.poo.mercado.comunicacao.dto.request.FuncionarioDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.FuncionarioDTOResponse;
import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private Fachada fachada;

    @Autowired
    private FuncionarioConversor conversor;

    @PostMapping
    public ResponseEntity<FuncionarioDTOResponse> cadastrar(@RequestBody FuncionarioDTORequest dto) {
        Funcionario funcionario = conversor.requestToEntity(dto);
        Funcionario salvo = fachada.salvarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversor.entityToResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTOResponse>> listarTodos() {
        List<Funcionario> funcionarios = fachada.listarFuncionarios();
        List<FuncionarioDTOResponse> response = funcionarios.stream()
                .map(conversor::entityToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTOResponse> buscarPorId(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        Funcionario funcionario = fachada.procurarFuncionarioId(id);
        return ResponseEntity.ok(conversor.entityToResponse(funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) throws EntidadeNaoEncontradaException {
        fachada.removerFuncionarioId(id);
        return ResponseEntity.noContent().build();
    }
}