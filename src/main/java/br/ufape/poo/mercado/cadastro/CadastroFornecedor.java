package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.repository.FornecedorRepository;

@Service
public class CadastroFornecedor implements InterfaceCadastroFornecedor {
    @Autowired
    private FornecedorRepository colecaoFornecedor;

    public Fornecedor salvarFornecedor(Fornecedor entity) {
        return colecaoFornecedor.save(entity);
    }
    public Fornecedor procurarFornecedorId(Integer id) throws EntidadeNaoEncontradaException {
        Fornecedor f = colecaoFornecedor.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return f;
    }
    public List<Fornecedor> listarFornecedores() {
        return colecaoFornecedor.findAll();
    }
    public boolean verificarExistenciaFornecedorId(Integer id) {
        return colecaoFornecedor.existsById(id);
    }
    public void removerFornecedorId(Integer id) throws EntidadeNaoEncontradaException {
        Fornecedor f = colecaoFornecedor.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoFornecedor.deleteById(id);
    }
    @Override
    public Fornecedor atualizar(Integer id, Fornecedor fornecedorAtualizado) throws EntidadeNaoEncontradaException {
        Fornecedor fornecedorExistente = procurarFornecedorId(id);

        // Atualize os atributos da sua entidade Fornecedor
        fornecedorExistente.setNome(fornecedorAtualizado.getNome());
        fornecedorExistente.setCnpj(fornecedorAtualizado.getCnpj());
        fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
        fornecedorExistente.setEmail(fornecedorAtualizado.getEmail());

        return colecaoFornecedor.save(fornecedorExistente);
    }
}