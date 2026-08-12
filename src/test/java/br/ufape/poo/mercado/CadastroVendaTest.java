package br.ufape.poo.mercado;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import br.ufape.poo.mercado.cadastro.InterfaceCadastroProduto;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroVenda;
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Venda
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroVendaTest {

    @Autowired
    private InterfaceCadastroVenda cadastroVenda;

    @Autowired
    private InterfaceCadastroProduto cadastroProduto;

    // Verifica se os dados da Venda estão sendo salvos corretamente
    @Test
    void testarSalvarVenda() {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Arroz",
                "Arroz branco tipo 1",
                "Marca A",
                25.90,
                "7891234567001",
                Date.valueOf("2027-08-11")
        );

        // Salva o Produto antes de associá-lo à Venda
        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Criação da Venda para teste
        Venda venda = new Venda();
        venda.setDataVenda("11/08/2026");
        venda.setValorTotal(51.80);
        venda.setQuantidadeProdutos(2);
        venda.setDesconto(0.0);
        venda.setProduto(produtoSalvo);

        // Salva a Venda
        Venda vendaSalva =
                cadastroVenda.salvarVenda(venda);

        // Verifica se a Venda foi salva corretamente
        assertNotNull(vendaSalva);
        assertNotNull(vendaSalva.getId());

        assertEquals(
                "11/08/2026",
                vendaSalva.getDataVenda()
        );

        assertEquals(
                51.80,
                vendaSalva.getValorTotal(),
                0.001
        );

        assertEquals(
                2,
                vendaSalva.getQuantidadeProdutos()
        );

        assertEquals(
                0.0,
                vendaSalva.getDesconto(),
                0.001
        );

        assertNotNull(vendaSalva.getProduto());

        assertEquals(
                produtoSalvo.getCodigo(),
                vendaSalva.getProduto().getCodigo()
        );
    }

    // Verifica se é possível procurar uma Venda utilizando seu ID
    @Test
    void testarProcurarVendaId()
            throws EntidadeNaoEncontradaException {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Feijão",
                "Feijão carioca",
                "Marca B",
                9.50,
                "7891234567002",
                Date.valueOf("2027-05-20")
        );

        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Criação da Venda para teste
        Venda venda = new Venda();
        venda.setDataVenda("11/08/2026");
        venda.setValorTotal(28.50);
        venda.setQuantidadeProdutos(3);
        venda.setDesconto(5.0);
        venda.setProduto(produtoSalvo);

        // Salva a Venda para gerar um ID
        Venda vendaSalva =
                cadastroVenda.salvarVenda(venda);

        // Procura a Venda pelo ID
        Venda vendaEncontrada =
                cadastroVenda.procurarVendaId(
                        vendaSalva.getId()
                );

        // Verifica se a Venda encontrada possui os dados esperados
        assertNotNull(vendaEncontrada);

        assertEquals(
                vendaSalva.getId(),
                vendaEncontrada.getId()
        );

        assertEquals(
                "11/08/2026",
                vendaEncontrada.getDataVenda()
        );

        assertEquals(
                28.50,
                vendaEncontrada.getValorTotal(),
                0.001
        );

        assertEquals(
                3,
                vendaEncontrada.getQuantidadeProdutos()
        );

        assertEquals(
                5.0,
                vendaEncontrada.getDesconto(),
                0.001
        );

        assertNotNull(vendaEncontrada.getProduto());

        assertEquals(
                produtoSalvo.getCodigo(),
                vendaEncontrada.getProduto().getCodigo()
        );
    }

    // Verifica se é possível listar as Vendas cadastradas
    @Test
    void testarListarVendas() {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Macarrão",
                "Macarrão espaguete",
                "Marca C",
                6.50,
                "7891234567003",
                Date.valueOf("2027-10-10")
        );

        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Criação das Vendas para teste
        Venda venda1 = new Venda();
        venda1.setDataVenda("10/08/2026");
        venda1.setValorTotal(13.00);
        venda1.setQuantidadeProdutos(2);
        venda1.setDesconto(0.0);
        venda1.setProduto(produtoSalvo);

        Venda venda2 = new Venda();
        venda2.setDataVenda("11/08/2026");
        venda2.setValorTotal(19.50);
        venda2.setQuantidadeProdutos(3);
        venda2.setDesconto(0.0);
        venda2.setProduto(produtoSalvo);

        // Salva as Vendas
        cadastroVenda.salvarVenda(venda1);
        cadastroVenda.salvarVenda(venda2);

        // Lista as Vendas
        List<Venda> vendas =
                cadastroVenda.listarVendas();

        // Verifica se as Vendas foram encontradas
        assertNotNull(vendas);
        assertTrue(vendas.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de uma Venda
    @Test
    void testarVerificarExistenciaVendaId() {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Açúcar",
                "Açúcar refinado",
                "Marca D",
                4.90,
                "7891234567004",
                Date.valueOf("2028-01-01")
        );

        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Criação da Venda para teste
        Venda venda = new Venda();
        venda.setDataVenda("11/08/2026");
        venda.setValorTotal(9.80);
        venda.setQuantidadeProdutos(2);
        venda.setDesconto(0.0);
        venda.setProduto(produtoSalvo);

        // Salva a Venda
        Venda vendaSalva =
                cadastroVenda.salvarVenda(venda);

        // Verifica se a Venda existe pelo ID
        boolean existe =
                cadastroVenda.verificarExistenciaVendaId(
                        vendaSalva.getId()
                );

        assertTrue(existe);
    }

    // Verifica se uma Venda pode ser removida utilizando seu ID
    @Test
    void testarRemoverVendaId()
            throws EntidadeNaoEncontradaException {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Café",
                "Café torrado e moído",
                "Marca E",
                18.90,
                "7891234567005",
                Date.valueOf("2027-06-30")
        );

        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Criação da Venda para teste
        Venda venda = new Venda();
        venda.setDataVenda("11/08/2026");
        venda.setValorTotal(37.80);
        venda.setQuantidadeProdutos(2);
        venda.setDesconto(0.0);
        venda.setProduto(produtoSalvo);

        // Salva a Venda
        Venda vendaSalva =
                cadastroVenda.salvarVenda(venda);

        Integer id = vendaSalva.getId();

        // Remove a Venda pelo ID
        cadastroVenda.removerVendaId(id);

        // Verifica se a Venda foi removida
        assertFalse(
                cadastroVenda.verificarExistenciaVendaId(id)
        );
    }

    // Verifica se procurar uma Venda inexistente lança a exceção esperada
    @Test
    void testarProcurarVendaInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroVenda.procurarVendaId(
                        idInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}