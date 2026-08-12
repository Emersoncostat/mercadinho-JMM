package br.ufape.poo.mercado;

import java.time.LocalDate;
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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroLote;
import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Lote
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroLoteTest {

    @Autowired
    private InterfaceCadastroLote cadastroLote;

    // Verifica se os dados do Lote estão sendo salvos corretamente
    @Test
    void testarSalvarLote() {

        // Criação do Lote para teste
        Lote lote = new Lote();
        lote.setCategoriaDoProduto("Alimentos");
        lote.setMarcaDoProduto("Marca A");
        lote.setQuantidade(100);
        lote.setCodigo(1001);
        lote.setValorTotalDoLote(2500.0);
        lote.setFabricacao(LocalDate.of(2026, 8, 1));
        lote.setValidade(LocalDate.of(2027, 8, 1));

        // Salva o Lote
        Lote loteSalvo = cadastroLote.salvarLote(lote);

        // Verifica se o Lote foi salvo corretamente
        assertNotNull(loteSalvo);
        assertNotNull(loteSalvo.getId());

        assertEquals(
                "Alimentos",
                loteSalvo.getCategoriaDoProduto()
        );

        assertEquals(
                "Marca A",
                loteSalvo.getMarcaDoProduto()
        );

        assertEquals(
                100,
                loteSalvo.getQuantidade()
        );

        assertEquals(
                1001,
                loteSalvo.getCodigo()
        );

        assertEquals(
                2500.0,
                loteSalvo.getValorTotalDoLote(),
                0.001
        );

        assertEquals(
                LocalDate.of(2026, 8, 1),
                loteSalvo.getFabricacao()
        );

        assertEquals(
                LocalDate.of(2027, 8, 1),
                loteSalvo.getValidade()
        );
    }

    // Verifica se é possível procurar um Lote utilizando seu ID
    @Test
    void testarProcurarLoteId()
            throws EntidadeNaoEncontradaException {

        // Criação do Lote para teste
        Lote lote = new Lote();
        lote.setCategoriaDoProduto("Bebidas");
        lote.setMarcaDoProduto("Marca B");
        lote.setQuantidade(200);
        lote.setCodigo(2002);
        lote.setValorTotalDoLote(4000.0);
        lote.setFabricacao(LocalDate.of(2026, 7, 10));
        lote.setValidade(LocalDate.of(2027, 7, 10));

        // Salva o Lote para gerar um ID
        Lote loteSalvo = cadastroLote.salvarLote(lote);

        // Procura o Lote pelo ID
        Lote loteEncontrado =
                cadastroLote.procurarLoteId(loteSalvo.getId());

        // Verifica se o Lote encontrado possui os dados esperados
        assertNotNull(loteEncontrado);

        assertEquals(
                loteSalvo.getId(),
                loteEncontrado.getId()
        );

        assertEquals(
                "Bebidas",
                loteEncontrado.getCategoriaDoProduto()
        );

        assertEquals(
                "Marca B",
                loteEncontrado.getMarcaDoProduto()
        );

        assertEquals(
                200,
                loteEncontrado.getQuantidade()
        );

        assertEquals(
                2002,
                loteEncontrado.getCodigo()
        );

        assertEquals(
                4000.0,
                loteEncontrado.getValorTotalDoLote(),
                0.001
        );

        assertEquals(
                LocalDate.of(2026, 7, 10),
                loteEncontrado.getFabricacao()
        );

        assertEquals(
                LocalDate.of(2027, 7, 10),
                loteEncontrado.getValidade()
        );
    }

    // Verifica se é possível listar os Lotes cadastrados
    @Test
    void testarListarLotes() {

        // Criação dos Lotes para teste
        Lote lote1 = new Lote();
        lote1.setCategoriaDoProduto("Limpeza");
        lote1.setMarcaDoProduto("Marca C");
        lote1.setQuantidade(50);
        lote1.setCodigo(3003);
        lote1.setValorTotalDoLote(1000.0);
        lote1.setFabricacao(LocalDate.of(2026, 6, 1));
        lote1.setValidade(LocalDate.of(2028, 6, 1));

        Lote lote2 = new Lote();
        lote2.setCategoriaDoProduto("Higiene");
        lote2.setMarcaDoProduto("Marca D");
        lote2.setQuantidade(80);
        lote2.setCodigo(4004);
        lote2.setValorTotalDoLote(1600.0);
        lote2.setFabricacao(LocalDate.of(2026, 5, 1));
        lote2.setValidade(LocalDate.of(2028, 5, 1));

        // Salva os Lotes
        cadastroLote.salvarLote(lote1);
        cadastroLote.salvarLote(lote2);

        // Lista os Lotes
        List<Lote> lotes = cadastroLote.listarLotes();

        // Verifica se os Lotes foram encontrados
        assertNotNull(lotes);
        assertTrue(lotes.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Lote
    @Test
    void testarVerificarExistenciaLoteId() {

        // Criação do Lote para teste
        Lote lote = new Lote();
        lote.setCategoriaDoProduto("Congelados");
        lote.setMarcaDoProduto("Marca E");
        lote.setQuantidade(60);
        lote.setCodigo(5005);
        lote.setValorTotalDoLote(1800.0);
        lote.setFabricacao(LocalDate.of(2026, 8, 5));
        lote.setValidade(LocalDate.of(2027, 2, 5));

        // Salva o Lote
        Lote loteSalvo = cadastroLote.salvarLote(lote);

        // Verifica se o Lote existe pelo ID
        boolean existe =
                cadastroLote.verificarExistenciaLoteId(
                        loteSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Lote pode ser removido utilizando seu ID
    @Test
    void testarRemoverLoteId()
            throws EntidadeNaoEncontradaException {

        // Criação do Lote para teste
        Lote lote = new Lote();
        lote.setCategoriaDoProduto("Padaria");
        lote.setMarcaDoProduto("Marca F");
        lote.setQuantidade(40);
        lote.setCodigo(6006);
        lote.setValorTotalDoLote(900.0);
        lote.setFabricacao(LocalDate.of(2026, 8, 10));
        lote.setValidade(LocalDate.of(2026, 8, 20));

        // Salva o Lote
        Lote loteSalvo = cadastroLote.salvarLote(lote);

        Long id = loteSalvo.getId();

        // Remove o Lote pelo ID
        cadastroLote.removerLoteId(id);

        // Verifica se o Lote foi removido
        assertFalse(
                cadastroLote.verificarExistenciaLoteId(id)
        );
    }

    // Verifica se procurar um Lote inexistente lança a exceção esperada
    @Test
    void testarProcurarLoteInexistente() {

        // Define um ID que não existe
        Long idInexistente = 999999L;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroLote.procurarLoteId(idInexistente)
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}