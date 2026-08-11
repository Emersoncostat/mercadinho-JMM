package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.repository.FornecedorRepository;

@Service
public class CadastroFornecedor {

    @Autowired
    private FornecedorRepository colecaoFornecedor;

    public Fornecedor salvarFornecedor(Fornecedor entity) {
        return colecaoFornecedor.save(entity);
    }

    public Fornecedor procurarFornecedorId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoFornecedor.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Fornecedor não encontrado com o ID: " + id));
    }

    public List<Fornecedor> listarFornecedores() {
        return colecaoFornecedor.findAll();
    }

    public boolean verificarExistenciaFornecedorId(Integer id) {
        return colecaoFornecedor.existsById(id);
    }

    public void removerFornecedorId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaFornecedorId(id)) {
            throw new ObjetoNaoEncontradoException("Fornecedor não encontrado com o ID: " + id);
        }
        colecaoFornecedor.deleteById(id);
    }
}