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
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Produto
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroProdutoTest {

    @Autowired
    private InterfaceCadastroProduto cadastroProduto;

    // Verifica se os dados do Produto estão sendo salvos corretamente
    @Test
    void testarSalvarProduto() {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Arroz",
                "Arroz branco tipo 1",
                "Marca A",
                25.90,
                "7891234567890",
                Date.valueOf("2027-08-11")
        );

        // Salva o Produto
        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Verifica se o Produto foi salvo corretamente
        assertNotNull(produtoSalvo);
        assertNotNull(produtoSalvo.getCodigo());

        assertEquals(
                "Arroz",
                produtoSalvo.getNome()
        );

        assertEquals(
                "Arroz branco tipo 1",
                produtoSalvo.getDescricao()
        );

        assertEquals(
                "Marca A",
                produtoSalvo.getMarca()
        );

        assertEquals(
                25.90,
                produtoSalvo.getPreco(),
                0.001
        );

        assertEquals(
                "7891234567890",
                produtoSalvo.getCodigoBarras()
        );

        assertEquals(
                Date.valueOf("2027-08-11"),
                produtoSalvo.getValidade()
        );
    }

    // Verifica se é possível procurar um Produto utilizando seu código
    @Test
    void testarProcurarProdutoId()
            throws EntidadeNaoEncontradaException {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Feijão",
                "Feijão carioca",
                "Marca B",
                9.50,
                "7891234567891",
                Date.valueOf("2027-05-20")
        );

        // Salva o Produto para gerar um código
        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Procura o Produto pelo código
        Produto produtoEncontrado =
                cadastroProduto.procurarProdutoId(
                        produtoSalvo.getCodigo()
                );

        // Verifica se o Produto encontrado possui os dados esperados
        assertNotNull(produtoEncontrado);

        assertEquals(
                produtoSalvo.getCodigo(),
                produtoEncontrado.getCodigo()
        );

        assertEquals(
                "Feijão",
                produtoEncontrado.getNome()
        );

        assertEquals(
                "Feijão carioca",
                produtoEncontrado.getDescricao()
        );

        assertEquals(
                "Marca B",
                produtoEncontrado.getMarca()
        );

        assertEquals(
                9.50,
                produtoEncontrado.getPreco(),
                0.001
        );

        assertEquals(
                "7891234567891",
                produtoEncontrado.getCodigoBarras()
        );

        assertEquals(
                Date.valueOf("2027-05-20"),
                produtoEncontrado.getValidade()
        );
    }

    // Verifica se é possível listar os Produtos cadastrados
    @Test
    void testarListarProdutos() {

        // Criação dos Produtos para teste
        Produto produto1 = new Produto(
                "Macarrão",
                "Macarrão espaguete",
                "Marca C",
                6.50,
                "7891234567892",
                Date.valueOf("2027-10-10")
        );

        Produto produto2 = new Produto(
                "Leite",
                "Leite integral",
                "Marca D",
                5.75,
                "7891234567893",
                Date.valueOf("2026-12-15")
        );

        // Salva os Produtos
        cadastroProduto.salvarProduto(produto1);
        cadastroProduto.salvarProduto(produto2);

        // Lista os Produtos
        List<Produto> produtos =
                cadastroProduto.listarProdutos();

        // Verifica se os Produtos foram encontrados
        assertNotNull(produtos);
        assertTrue(produtos.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Produto
    @Test
    void testarVerificarExistenciaProdutoId() {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Açúcar",
                "Açúcar refinado",
                "Marca E",
                4.90,
                "7891234567894",
                Date.valueOf("2028-01-01")
        );

        // Salva o Produto
        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        // Verifica se o Produto existe pelo código
        boolean existe =
                cadastroProduto.verificarExistenciaProdutoId(
                        produtoSalvo.getCodigo()
                );

        assertTrue(existe);
    }

    // Verifica se um Produto pode ser removido utilizando seu código
    @Test
    void testarRemoverProdutoId()
            throws EntidadeNaoEncontradaException {

        // Criação do Produto para teste
        Produto produto = new Produto(
                "Café",
                "Café torrado e moído",
                "Marca F",
                18.90,
                "7891234567895",
                Date.valueOf("2027-06-30")
        );

        // Salva o Produto
        Produto produtoSalvo =
                cadastroProduto.salvarProduto(produto);

        Integer codigo = produtoSalvo.getCodigo();

        // Remove o Produto pelo código
        cadastroProduto.removerProdutoId(codigo);

        // Verifica se o Produto foi removido
        assertFalse(
                cadastroProduto.verificarExistenciaProdutoId(codigo)
        );
    }

    // Verifica se procurar um Produto inexistente lança a exceção esperada
    @Test
    void testarProcurarProdutoInexistente() {

        // Define um código que não existe
        Integer codigoInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroProduto.procurarProdutoId(
                        codigoInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}