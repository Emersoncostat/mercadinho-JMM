package br.ufape.poo.mercado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import br.ufape.poo.mercado.cadastro.InterfaceCadastroEstoque;
import br.ufape.poo.mercado.model.Estoque;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Estoque
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroEstoqueTest {

    @Autowired
    private InterfaceCadastroEstoque cadastroEstoque;

    // Verifica se os dados do Estoque estão sendo salvos corretamente
    @Test
    void testarSalvarEstoque() {

        // Criação do Estoque para teste
        Estoque estoque = new Estoque();
        estoque.setQuantidadeDisponivel(100);
        estoque.setEstoqueMinimo(20);
        estoque.setEstoqueMaximo(200);
        estoque.setDataAtualizacao("11/08/2026");

        // Salva o Estoque
        Estoque estoqueSalvo = cadastroEstoque.salvarEstoque(estoque);

        // Verifica se o Estoque foi salvo corretamente
        assertNotNull(estoqueSalvo);
        assertNotNull(estoqueSalvo.getId());
        assertEquals(100, estoqueSalvo.getQuantidadeDisponivel());
        assertEquals(20, estoqueSalvo.getEstoqueMinimo());
        assertEquals(200, estoqueSalvo.getEstoqueMaximo());
        assertEquals("11/08/2026", estoqueSalvo.getDataAtualizacao());
    }

    // Verifica se é possível procurar um Estoque utilizando seu ID
    @Test
    void testarProcurarEstoqueId() throws EntidadeNaoEncontradaException {

        // Criação do Estoque para teste
        Estoque estoque = new Estoque();
        estoque.setQuantidadeDisponivel(150);
        estoque.setEstoqueMinimo(30);
        estoque.setEstoqueMaximo(300);
        estoque.setDataAtualizacao("11/08/2026");

        // Salva o Estoque para gerar um ID
        Estoque estoqueSalvo = cadastroEstoque.salvarEstoque(estoque);

        // Procura o Estoque pelo ID
        Estoque estoqueEncontrado =
                cadastroEstoque.procurarEstoqueId(estoqueSalvo.getId());

        // Verifica se o Estoque encontrado possui os dados esperados
        assertNotNull(estoqueEncontrado);
        assertEquals(estoqueSalvo.getId(), estoqueEncontrado.getId());
        assertEquals(150, estoqueEncontrado.getQuantidadeDisponivel());
        assertEquals(30, estoqueEncontrado.getEstoqueMinimo());
        assertEquals(300, estoqueEncontrado.getEstoqueMaximo());
        assertEquals("11/08/2026", estoqueEncontrado.getDataAtualizacao());
    }

    // Verifica se é possível listar os Estoques cadastrados
    @Test
    void testarListarEstoques() {

        // Criação dos Estoques para teste
        Estoque estoque1 = new Estoque();
        estoque1.setQuantidadeDisponivel(50);
        estoque1.setEstoqueMinimo(10);
        estoque1.setEstoqueMaximo(100);
        estoque1.setDataAtualizacao("10/08/2026");

        Estoque estoque2 = new Estoque();
        estoque2.setQuantidadeDisponivel(80);
        estoque2.setEstoqueMinimo(15);
        estoque2.setEstoqueMaximo(150);
        estoque2.setDataAtualizacao("11/08/2026");

        // Salva os Estoques
        cadastroEstoque.salvarEstoque(estoque1);
        cadastroEstoque.salvarEstoque(estoque2);

        // Lista os Estoques
        List<Estoque> estoques = cadastroEstoque.listarEstoques();

        // Verifica se os Estoques foram encontrados
        assertNotNull(estoques);
        assertTrue(estoques.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Estoque
    @Test
    void testarVerificarExistenciaEstoqueId() {

        // Criação do Estoque para teste
        Estoque estoque = new Estoque();
        estoque.setQuantidadeDisponivel(75);
        estoque.setEstoqueMinimo(10);
        estoque.setEstoqueMaximo(120);
        estoque.setDataAtualizacao("11/08/2026");

        // Salva o Estoque
        Estoque estoqueSalvo = cadastroEstoque.salvarEstoque(estoque);

        // Verifica se o Estoque existe pelo ID
        boolean existe =
                cadastroEstoque.verificarExistenciaEstoqueId(
                        estoqueSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Estoque pode ser removido utilizando seu ID
    @Test
    void testarRemoverEstoqueId() throws EntidadeNaoEncontradaException {

        // Criação do Estoque para teste
        Estoque estoque = new Estoque();
        estoque.setQuantidadeDisponivel(40);
        estoque.setEstoqueMinimo(5);
        estoque.setEstoqueMaximo(80);
        estoque.setDataAtualizacao("11/08/2026");

        // Salva o Estoque
        Estoque estoqueSalvo = cadastroEstoque.salvarEstoque(estoque);
        Integer id = estoqueSalvo.getId();

        // Remove o Estoque pelo ID
        cadastroEstoque.removerEstoqueId(id);

        // Verifica se o Estoque foi removido
        assertFalse(
                cadastroEstoque.verificarExistenciaEstoqueId(id)
        );
    }

    // Verifica se procurar um Estoque inexistente lança a exceção esperada
    @Test
    void testarProcurarEstoqueInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroEstoque.procurarEstoqueId(idInexistente)
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}