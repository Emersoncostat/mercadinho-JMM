package br.ufape.poo.mercado.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.repository.VendaRepository;

@Service
public class CadastroVenda implements InterfaceCadastroVenda {

    @Autowired
    private VendaRepository colecaoVenda;

    @Override
    public Venda salvarVenda(Venda entity) {
        return colecaoVenda.save(entity);
    }

    @Override
    public Venda procurarVendaId(Integer id)
            throws EntidadeNaoEncontradaException {

        Venda v = colecaoVenda.findById(id).orElse(null);

        if (v == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        return v;
    }

    @Override
    public List<Venda> listarVendas() {
        return colecaoVenda.findAll();
    }

    @Override
    public boolean verificarExistenciaVendaId(Integer id) {
        return colecaoVenda.existsById(id);
    }

    @Override
    public void removerVendaId(Integer id)
            throws EntidadeNaoEncontradaException {

        Venda v = colecaoVenda.findById(id).orElse(null);

        if (v == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        colecaoVenda.deleteById(id);
    }
}