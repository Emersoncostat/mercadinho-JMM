package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.repository.LoteRepository;

@Service
public class CadastroLote {

    @Autowired
    private LoteRepository colecaoLote;

    public Lote salvarLote(Lote entity) {
        return colecaoLote.save(entity);
    }
    public Lote procurarLoteId(Long id) throws EntidadeNaoEncontradaException {
        Lote l = colecaoLote.findById(id).orElse(null);
        if (l == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        return l;
    }
    public List<Lote> listarLotes() {
        return colecaoLote.findAll();
    }
    public boolean verificarExistenciaLoteId(Long id) {
        return colecaoLote.existsById(id);
    }
    public void removerLoteId(Long id) throws EntidadeNaoEncontradaException {
        Lote l = colecaoLote.findById(id).orElse(null);
        if (l == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }
        colecaoLote.deleteById(id);
    }
}