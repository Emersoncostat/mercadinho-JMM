package br.ufape.poo.mercado;

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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroFinanceiro;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Financeiro
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroFinanceiroTest {

    @Autowired
    private InterfaceCadastroFinanceiro cadastroFinanceiro;

    // Verifica se os dados do Financeiro estão sendo salvos corretamente
    @Test
    void testarSalvarFinanceiro() {

        // Criação do Financeiro para teste
        Financeiro financeiro = new Financeiro();
        financeiro.setReceita(5000.0);
        financeiro.setDespesa(2000.0);
        financeiro.setLucro(3000.0);
        financeiro.setDataRegistro("11/08/2026");

        // Salva o Financeiro
        Financeiro financeiroSalvo =
                cadastroFinanceiro.salvarFinanceiro(financeiro);

        // Verifica se o Financeiro foi salvo corretamente
        assertNotNull(financeiroSalvo);
        assertNotNull(financeiroSalvo.getId());

        assertEquals(
                5000.0,
                financeiroSalvo.getReceita(),
                0.001
        );

        assertEquals(
                2000.0,
                financeiroSalvo.getDespesa(),
                0.001
        );

        assertEquals(
                3000.0,
                financeiroSalvo.getLucro(),
                0.001
        );

        assertEquals(
                "11/08/2026",
                financeiroSalvo.getDataRegistro()
        );
    }

    // Verifica se é possível procurar um Financeiro utilizando seu ID
    @Test
    void testarProcurarFinanceiroId()
            throws EntidadeNaoEncontradaException {

        // Criação do Financeiro para teste
        Financeiro financeiro = new Financeiro();
        financeiro.setReceita(8000.0);
        financeiro.setDespesa(3500.0);
        financeiro.setLucro(4500.0);
        financeiro.setDataRegistro("11/08/2026");

        // Salva o Financeiro para gerar um ID
        Financeiro financeiroSalvo =
                cadastroFinanceiro.salvarFinanceiro(financeiro);

        // Procura o Financeiro pelo ID
        Financeiro financeiroEncontrado =
                cadastroFinanceiro.procurarFinanceiroId(
                        financeiroSalvo.getId()
                );

        // Verifica se o Financeiro encontrado possui os dados esperados
        assertNotNull(financeiroEncontrado);

        assertEquals(
                financeiroSalvo.getId(),
                financeiroEncontrado.getId()
        );

        assertEquals(
                8000.0,
                financeiroEncontrado.getReceita(),
                0.001
        );

        assertEquals(
                3500.0,
                financeiroEncontrado.getDespesa(),
                0.001
        );

        assertEquals(
                4500.0,
                financeiroEncontrado.getLucro(),
                0.001
        );

        assertEquals(
                "11/08/2026",
                financeiroEncontrado.getDataRegistro()
        );
    }

    // Verifica se é possível listar os Financeiros cadastrados
    @Test
    void testarListarFinanceiros() {

        // Criação dos Financeiros para teste
        Financeiro financeiro1 = new Financeiro();
        financeiro1.setReceita(3000.0);
        financeiro1.setDespesa(1000.0);
        financeiro1.setLucro(2000.0);
        financeiro1.setDataRegistro("10/08/2026");

        Financeiro financeiro2 = new Financeiro();
        financeiro2.setReceita(7000.0);
        financeiro2.setDespesa(2500.0);
        financeiro2.setLucro(4500.0);
        financeiro2.setDataRegistro("11/08/2026");

        // Salva os Financeiros
        cadastroFinanceiro.salvarFinanceiro(financeiro1);
        cadastroFinanceiro.salvarFinanceiro(financeiro2);

        // Lista os Financeiros
        List<Financeiro> financeiros =
                cadastroFinanceiro.listarFinanceiros();

        // Verifica se os Financeiros foram encontrados
        assertNotNull(financeiros);
        assertTrue(financeiros.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Financeiro
    @Test
    void testarVerificarExistenciaFinanceiroId() {

        // Criação do Financeiro para teste
        Financeiro financeiro = new Financeiro();
        financeiro.setReceita(4000.0);
        financeiro.setDespesa(1500.0);
        financeiro.setLucro(2500.0);
        financeiro.setDataRegistro("11/08/2026");

        // Salva o Financeiro
        Financeiro financeiroSalvo =
                cadastroFinanceiro.salvarFinanceiro(financeiro);

        // Verifica se o Financeiro existe pelo ID
        boolean existe =
                cadastroFinanceiro.verificarExistenciaFinanceiroId(
                        financeiroSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Financeiro pode ser removido utilizando seu ID
    @Test
    void testarRemoverFinanceiroId()
            throws EntidadeNaoEncontradaException {

        // Criação do Financeiro para teste
        Financeiro financeiro = new Financeiro();
        financeiro.setReceita(6000.0);
        financeiro.setDespesa(3000.0);
        financeiro.setLucro(3000.0);
        financeiro.setDataRegistro("11/08/2026");

        // Salva o Financeiro
        Financeiro financeiroSalvo =
                cadastroFinanceiro.salvarFinanceiro(financeiro);

        Integer id = financeiroSalvo.getId();

        // Remove o Financeiro pelo ID
        cadastroFinanceiro.removerFinanceiroId(id);

        // Verifica se o Financeiro foi removido
        assertFalse(
                cadastroFinanceiro.verificarExistenciaFinanceiroId(id)
        );
    }

    // Verifica se procurar um Financeiro inexistente lança a exceção esperada
    @Test
    void testarProcurarFinanceiroInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroFinanceiro.procurarFinanceiroId(
                        idInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}