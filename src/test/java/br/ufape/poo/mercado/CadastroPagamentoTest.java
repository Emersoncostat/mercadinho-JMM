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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroPagamento;
import br.ufape.poo.mercado.model.Pagamento;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Pagamento
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroPagamentoTest {

    @Autowired
    private InterfaceCadastroPagamento cadastroPagamento;

    // Verifica se os dados do Pagamento estão sendo salvos corretamente
    @Test
    void testarSalvarPagamento() {

        // Criação do Pagamento para teste
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento("Dinheiro");
        pagamento.setValorPago(100.0);
        pagamento.setDataPagamento("11/08/2026");
        pagamento.setStatus("Pago");
        pagamento.setTroco(20.0);

        // Salva o Pagamento
        Pagamento pagamentoSalvo =
                cadastroPagamento.salvarPagamento(pagamento);

        // Verifica se o Pagamento foi salvo corretamente
        assertNotNull(pagamentoSalvo);
        assertNotNull(pagamentoSalvo.getId());

        assertEquals(
                "Dinheiro",
                pagamentoSalvo.getTipoPagamento()
        );

        assertEquals(
                100.0,
                pagamentoSalvo.getValorPago(),
                0.001
        );

        assertEquals(
                "11/08/2026",
                pagamentoSalvo.getDataPagamento()
        );

        assertEquals(
                "Pago",
                pagamentoSalvo.getStatus()
        );

        assertEquals(
                20.0,
                pagamentoSalvo.getTroco(),
                0.001
        );
    }

    // Verifica se é possível procurar um Pagamento utilizando seu ID
    @Test
    void testarProcurarPagamentoId()
            throws EntidadeNaoEncontradaException {

        // Criação do Pagamento para teste
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento("Cartão de Crédito");
        pagamento.setValorPago(250.0);
        pagamento.setDataPagamento("11/08/2026");
        pagamento.setStatus("Pago");
        pagamento.setTroco(0.0);

        // Salva o Pagamento para gerar um ID
        Pagamento pagamentoSalvo =
                cadastroPagamento.salvarPagamento(pagamento);

        // Procura o Pagamento pelo ID
        Pagamento pagamentoEncontrado =
                cadastroPagamento.procurarPagamentoId(
                        pagamentoSalvo.getId()
                );

        // Verifica se o Pagamento encontrado possui os dados esperados
        assertNotNull(pagamentoEncontrado);

        assertEquals(
                pagamentoSalvo.getId(),
                pagamentoEncontrado.getId()
        );

        assertEquals(
                "Cartão de Crédito",
                pagamentoEncontrado.getTipoPagamento()
        );

        assertEquals(
                250.0,
                pagamentoEncontrado.getValorPago(),
                0.001
        );

        assertEquals(
                "11/08/2026",
                pagamentoEncontrado.getDataPagamento()
        );

        assertEquals(
                "Pago",
                pagamentoEncontrado.getStatus()
        );

        assertEquals(
                0.0,
                pagamentoEncontrado.getTroco(),
                0.001
        );
    }

    // Verifica se é possível listar os Pagamentos cadastrados
    @Test
    void testarListarPagamentos() {

        // Criação dos Pagamentos para teste
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setTipoPagamento("Pix");
        pagamento1.setValorPago(80.0);
        pagamento1.setDataPagamento("10/08/2026");
        pagamento1.setStatus("Pago");
        pagamento1.setTroco(0.0);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setTipoPagamento("Dinheiro");
        pagamento2.setValorPago(200.0);
        pagamento2.setDataPagamento("11/08/2026");
        pagamento2.setStatus("Pago");
        pagamento2.setTroco(15.0);

        // Salva os Pagamentos
        cadastroPagamento.salvarPagamento(pagamento1);
        cadastroPagamento.salvarPagamento(pagamento2);

        // Lista os Pagamentos
        List<Pagamento> pagamentos =
                cadastroPagamento.listarPagamentos();

        // Verifica se os Pagamentos foram encontrados
        assertNotNull(pagamentos);
        assertTrue(pagamentos.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Pagamento
    @Test
    void testarVerificarExistenciaPagamentoId() {

        // Criação do Pagamento para teste
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento("Débito");
        pagamento.setValorPago(150.0);
        pagamento.setDataPagamento("11/08/2026");
        pagamento.setStatus("Pago");
        pagamento.setTroco(0.0);

        // Salva o Pagamento
        Pagamento pagamentoSalvo =
                cadastroPagamento.salvarPagamento(pagamento);

        // Verifica se o Pagamento existe pelo ID
        boolean existe =
                cadastroPagamento.verificarExistenciaPagamentoId(
                        pagamentoSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Pagamento pode ser removido utilizando seu ID
    @Test
    void testarRemoverPagamentoId()
            throws EntidadeNaoEncontradaException {

        // Criação do Pagamento para teste
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento("Pix");
        pagamento.setValorPago(300.0);
        pagamento.setDataPagamento("11/08/2026");
        pagamento.setStatus("Pago");
        pagamento.setTroco(0.0);

        // Salva o Pagamento
        Pagamento pagamentoSalvo =
                cadastroPagamento.salvarPagamento(pagamento);

        Integer id = pagamentoSalvo.getId();

        // Remove o Pagamento pelo ID
        cadastroPagamento.removerPagamentoId(id);

        // Verifica se o Pagamento foi removido
        assertFalse(
                cadastroPagamento.verificarExistenciaPagamentoId(id)
        );
    }

    // Verifica se procurar um Pagamento inexistente lança a exceção esperada
    @Test
    void testarProcurarPagamentoInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroPagamento.procurarPagamentoId(
                        idInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}