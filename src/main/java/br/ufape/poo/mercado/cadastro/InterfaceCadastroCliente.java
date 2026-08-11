package br.ufape.poo.mercado.cadastro;

import java.util.List;

import br.ufape.poo.mercado.model.Cliente;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

public interface InterfaceCadastroCliente {

    Cliente salvarCliente(Cliente entity);

    Cliente procurarClienteId(Integer id) throws EntidadeNaoEncontradaException;
    
    List<Cliente> listarClientes();

    boolean verificarExistenciaClienteId(Integer id);

    void removerClienteId(Integer id) throws EntidadeNaoEncontradaException;
}
