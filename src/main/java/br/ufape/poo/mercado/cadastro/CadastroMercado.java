package br.ufape.poo.mercado.cadastro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import br.ufape.poo.mercado.repository.MercadoRepository;

@Service
public class CadastroMercado implements InterfaceCadastroMercado {

    @Autowired
    private MercadoRepository colecaoMercado;

    @Override
    public Mercado salvarMercado(Mercado entity) {
        return colecaoMercado.save(entity);
    }

    @Override
    public Mercado procurarMercadoId(Long id)
            throws EntidadeNaoEncontradaException {

        Mercado m = colecaoMercado.findById(id).orElse(null);

        if (m == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        return m;
    }

    @Override
    public List<Mercado> listarMercados() {
        return colecaoMercado.findAll();
    }

    @Override
    public boolean verificarExistenciaMercadoId(Long id) {
        return colecaoMercado.existsById(id);
    }

    @Override
    public void removerMercadoId(Long id)
            throws EntidadeNaoEncontradaException {

        Mercado m = colecaoMercado.findById(id).orElse(null);

        if (m == null) {
            throw new EntidadeNaoEncontradaException(String.valueOf(id));
        }

        colecaoMercado.deleteById(id);
    }
}