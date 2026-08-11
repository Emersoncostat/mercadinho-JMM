package br.ufape.poo.mercado;

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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroCaixa;
import br.ufape.poo.mercado.model.Caixa;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Caixa
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroCaixaTest {

    @Autowired
    private InterfaceCadastroCaixa cadastroCaixa;

    // Verifica se os dados do Caixa estão sendo salvos corretamente
    @Test
    void testarSalvarCaixa() {

        // Criação do Caixa para teste
        Caixa caixa = new Caixa();
        caixa.setSaldoInicial(100.0);
        caixa.setSaldoFinal(100.0);
        caixa.setDataAbertura("11/08/2026");
        caixa.setDataFechamento("11/08/2026");

        // Salva o Caixa
        Caixa caixaSalvo = cadastroCaixa.salvarCaixa(caixa);

        // Verifica se o Caixa foi salvo corretamente
        assertNotNull(caixaSalvo);
        assertNotNull(caixaSalvo.getId());
        assertEquals(100.0, caixaSalvo.getSaldoInicial(), 0.001);
        assertEquals(100.0, caixaSalvo.getSaldoFinal(), 0.001);
        assertEquals("11/08/2026", caixaSalvo.getDataAbertura());
        assertEquals("11/08/2026", caixaSalvo.getDataFechamento());
    }

    // Verifica se é possível procurar um Caixa utilizando seu ID
    @Test
    void testarProcurarCaixaId() throws EntidadeNaoEncontradaException {

        // Criação do Caixa para teste
        Caixa caixa = new Caixa();
        caixa.setSaldoInicial(200.0);
        caixa.setSaldoFinal(250.0);
        caixa.setDataAbertura("11/08/2026");

        // Salva o Caixa para gerar um ID
        Caixa caixaSalvo = cadastroCaixa.salvarCaixa(caixa);

        // Procura o Caixa pelo ID
        Caixa caixaEncontrado =
                cadastroCaixa.procurarCaixaId(caixaSalvo.getId());

        // Verifica se o Caixa encontrado possui os dados esperados
        assertNotNull(caixaEncontrado);
        assertEquals(caixaSalvo.getId(), caixaEncontrado.getId());
        assertEquals(200.0, caixaEncontrado.getSaldoInicial(), 0.001);
        assertEquals(250.0, caixaEncontrado.getSaldoFinal(), 0.001);
        assertEquals("11/08/2026", caixaEncontrado.getDataAbertura());
    }

    // Verifica se o cadastro identifica a existência de um Caixa
    @Test
    void testarVerificarExistenciaCaixaId() {

        // Criação do Caixa para teste
        Caixa caixa = new Caixa();
        caixa.setSaldoInicial(300.0);
        caixa.setSaldoFinal(300.0);

        // Salva o Caixa
        Caixa caixaSalvo = cadastroCaixa.salvarCaixa(caixa);

        // Verifica se o Caixa existe pelo ID
        boolean existe =
                cadastroCaixa.verificarExistenciaCaixaId(caixaSalvo.getId());

        assertTrue(existe);
    }

    // Verifica se um Caixa pode ser removido utilizando seu ID
    @Test
    void testarRemoverCaixaId() throws EntidadeNaoEncontradaException {

        // Criação do Caixa para teste
        Caixa caixa = new Caixa();
        caixa.setSaldoInicial(400.0);
        caixa.setSaldoFinal(450.0);

        // Salva o Caixa
        Caixa caixaSalvo = cadastroCaixa.salvarCaixa(caixa);
        Integer id = caixaSalvo.getId();

        // Remove o Caixa pelo ID
        cadastroCaixa.removerCaixaId(id);

        // Verifica se o Caixa foi removido
        assertFalse(cadastroCaixa.verificarExistenciaCaixaId(id));
    }

    // Verifica se procurar um Caixa inexistente lança a exceção esperada
    // Verifica se procurar um Caixa inexistente lança a exceção esperada
    @Test
    void testarProcurarCaixaInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroCaixa.procurarCaixaId(idInexistente)
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}