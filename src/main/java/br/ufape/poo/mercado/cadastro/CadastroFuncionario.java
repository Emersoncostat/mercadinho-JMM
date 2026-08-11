package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.repository.FuncionarioRepository;

@Service
public class CadastroFuncionario {

    @Autowired
    private FuncionarioRepository colecaoFuncionario;

    public Funcionario salvarFuncionario(Funcionario entity) {
        return colecaoFuncionario.save(entity);
    }
    public Funcionario procurarFuncionarioId(Integer id) throws EntidadeNaoEncontradaException {
        Funcionario f = colecaoFuncionario.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return f;
    }
    public List<Funcionario> listarFuncionarios() {
        return colecaoFuncionario.findAll();
    }
    public boolean verificarExistenciaFuncionarioId(Integer id) {
        return colecaoFuncionario.existsById(id);
    }
    public void removerFuncionarioId(Integer id) throws EntidadeNaoEncontradaException {
        Funcionario f = colecaoFuncionario.findById(id).orElse(null);
        if (f == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoFuncionario.deleteById(id);
    }
}