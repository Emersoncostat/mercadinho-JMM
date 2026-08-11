package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.repository.FuncionarioRepository;

@Service
public class CadastroFuncionario {

    @Autowired
    private FuncionarioRepository colecaoFuncionario;

    public Funcionario salvarFuncionario(Funcionario entity) {
        return colecaoFuncionario.save(entity);
    }

    public Funcionario procurarFuncionarioId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoFuncionario.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));
    }

    public List<Funcionario> listarFuncionarios() {
        return colecaoFuncionario.findAll();
    }

    public boolean verificarExistenciaFuncionarioId(Integer id) {
        return colecaoFuncionario.existsById(id);
    }

    public void removerFuncionarioId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaFuncionarioId(id)) {
            throw new ObjetoNaoEncontradoException("Funcionário não encontrado com o ID: " + id);
        }
        colecaoFuncionario.deleteById(id);
    }
}