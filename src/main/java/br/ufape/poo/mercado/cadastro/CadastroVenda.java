package br.ufape.poo.mercado.cadastro;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.exception.ObjetoNaoEncontradoException;
import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.repository.VendaRepository;

@Service
public class CadastroVenda {

    @Autowired
    private VendaRepository colecaoVenda;

    public Venda salvarVenda(Venda entity) {
        return colecaoVenda.save(entity);
    }

    public Venda procurarVendaId(Integer id) throws ObjetoNaoEncontradoException {
        return colecaoVenda.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Venda não encontrada com o ID: " + id));
    }

    public List<Venda> listarVendas() {
        return colecaoVenda.findAll();
    }

    public boolean verificarExistenciaVendaId(Integer id) {
        return colecaoVenda.existsById(id);
    }

    public void removerVendaId(Integer id) throws ObjetoNaoEncontradoException {
        if (!verificarExistenciaVendaId(id)) {
            throw new ObjetoNaoEncontradoException("Venda não encontrada com o ID: " + id);
        }
        colecaoVenda.deleteById(id);
    }
}