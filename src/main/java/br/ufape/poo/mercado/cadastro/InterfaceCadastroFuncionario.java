package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface  InterfaceCadastroFuncionario {
    
    Funcionario salvarFuncionario(Funcionario entity);

    Funcionario procurarFuncionarioId(Integer id) throws EntidadeNaoEncontradaException;

    List<Funcionario> listarFuncionarios();

    boolean verificarExistenciaFuncionarioId(Integer id);

    void removerFuncionarioId(Integer id) throws EntidadeNaoEncontradaException;
}
