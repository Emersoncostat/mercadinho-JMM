package br.ufape.poo.mercado.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.repository.FornecedorRepository;

@Service
public class CadastroFornecedor implements InterfaceCadastroFornecedor {

    @Autowired
    private FornecedorRepository colecaoFornecedor;

    @Override
    public Fornecedor salvarFornecedor(Fornecedor entity) {
        return colecaoFornecedor.save(entity);
    }

    @Override
    public Fornecedor procurarFornecedorId(Integer id)
            throws EntidadeNaoEncontradaException {

        Fornecedor f = colecaoFornecedor.findById(id).orElse(null);

        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        return f;
    }

    @Override
    public List<Fornecedor> listarFornecedores() {
        return colecaoFornecedor.findAll();
    }

    @Override
    public boolean verificarExistenciaFornecedorId(Integer id) {
        return colecaoFornecedor.existsById(id);
    }

    @Override
    public void removerFornecedorId(Integer id)
            throws EntidadeNaoEncontradaException {

        Fornecedor f = colecaoFornecedor.findById(id).orElse(null);

        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        colecaoFornecedor.deleteById(id);
    }
}