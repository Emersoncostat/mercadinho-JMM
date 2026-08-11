package br.ufape.poo.mercado.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.model.Cliente;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.repository.ClienteRepository;

@Service
public class CadastroCliente implements InterfaceCadastroCliente {

    @Autowired
    private ClienteRepository colecaoCliente;

    @Override
    public Cliente salvarCliente(Cliente entity) {
        return colecaoCliente.save(entity);
    }

    @Override
    public Cliente procurarClienteId(Integer id)
            throws EntidadeNaoEncontradaException {

        return colecaoCliente.findById(id)
                .orElseThrow(() ->
                        new EntidadeNaoEncontradaException(String.valueOf(id)));
    }

    @Override
    public List<Cliente> listarClientes() {
        return colecaoCliente.findAll();
    }

    @Override
    public boolean verificarExistenciaClienteId(Integer id) {
        return colecaoCliente.existsById(id);
    }

    @Override
    public void removerClienteId(Integer id)
            throws EntidadeNaoEncontradaException {

        if (!verificarExistenciaClienteId(id)) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        colecaoCliente.deleteById(id);
    }
}