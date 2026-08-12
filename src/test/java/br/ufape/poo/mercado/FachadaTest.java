package br.ufape.poo.mercado;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufape.poo.mercado.fachada.Fachada;
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

@SpringBootTest
class FachadaTest {

    @Autowired
    private Fachada fachada;

    private Produto produtoSalvo;

    @BeforeEach
    public void init() throws EntidadeNaoEncontradaException {
        // Criamos uma data válida para o SQL Date exigido no construtor do Produto
        Date validade = Date.valueOf("2027-12-31");

        // Instancia o produto passando todos os dados exigidos pelo construtor de Produto.java
        Produto p = new Produto("Arroz 1kg", "Arroz Integral", "Tio Joao", 5.0, "123456789", validade);

        // Salva o produto de teste diretamente pelo método da fachada
        this.produtoSalvo = fachada.salvarProduto(p);
    }

    @Test
    void testeRealizarVendaProduto() throws EntidadeNaoEncontradaException {
        assertNotNull(produtoSalvo, "O produto de teste deve ser criado com sucesso.");

        int totalVendasAntes = fachada.listarVendas().size();

        // Executa a regra de negócio complexa dos Tópicos 6 e 7
        Venda v = fachada.realizarVendaProduto(produtoSalvo.getCodigo(), 2, 10.0);

        int totalVendasDepois = fachada.listarVendas().size();

        // Verifica se a venda foi devidamente persistida
        assertEquals(totalVendasAntes + 1, totalVendasDepois);

        // Verifica se o cálculo matemático bateu (5.0 * 2 = 10.0 | Com 10% desc = 9.0)
        assertEquals(9.0, v.getValorTotal());
    }
}
