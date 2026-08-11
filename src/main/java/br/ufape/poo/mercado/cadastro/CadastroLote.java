package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.repository.LoteRepository;

@Service
public class CadastroLote {

    @Autowired
    private LoteRepository colecaoLote;

    public Lote salvarLote(Lote entity) {
        return colecaoLote.save(entity);
    }

    public Lote procurarLoteId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoLote.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Lote não encontrado com o ID: " + id));
    }

    public List<Lote> listarLotes() {
        return colecaoLote.findAll();
    }

    public boolean verificarExistenciaLoteId(Integer id) {
        return colecaoLote.existsById(id);
    }

    public void removerLoteId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaLoteId(id)) {
            throw new ObjetoNaoEncontradoException("Lote não encontrado com o ID: " + id);
        }
        colecaoLote.deleteById(id);
    }
}