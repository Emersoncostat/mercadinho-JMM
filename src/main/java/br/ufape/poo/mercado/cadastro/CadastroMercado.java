package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.repository.MercadoRepository;

@Service
public class CadastroMercado {

    @Autowired
    private MercadoRepository colecaoMercado;

    public Mercado salvarMercado(Mercado entity) {
        return colecaoMercado.save(entity);
    }

    public Mercado procurarMercadoId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoMercado.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mercado não encontrado com o ID: " + id));
    }

    public List<Mercado> listarMercados() {
        return colecaoMercado.findAll();
    }

    public boolean verificarExistenciaMercadoId(Integer id) {
        return colecaoMercado.existsById(id);
    }

    public void removerMercadoId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaMercadoId(id)) {
            throw new ObjetoNaoEncontradoException("Mercado não encontrado com o ID: " + id);
        }
        colecaoMercado.deleteById(id);
    }
}