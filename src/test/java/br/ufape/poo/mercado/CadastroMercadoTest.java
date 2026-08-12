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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroMercado;
import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Mercado
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroMercadoTest {

    @Autowired
    private InterfaceCadastroMercado cadastroMercado;

    // Verifica se os dados do Mercado estão sendo salvos corretamente
    @Test
    void testarSalvarMercado() {

        // Criação do Mercado para teste
        Mercado mercado = new Mercado();
        mercado.setNome("Mercadinho JMM");
        mercado.setCnpj("12.345.678/0001-90");
        mercado.setEndereco("Rua Central, 100");
        mercado.setTelefone("(87) 99999-1111");
        mercado.setEmail("contato@jmm.com");
        mercado.setHorarioFuncionamento("08:00 às 20:00");

        // Salva o Mercado
        Mercado mercadoSalvo =
                cadastroMercado.salvarMercado(mercado);

        // Verifica se o Mercado foi salvo corretamente
        assertNotNull(mercadoSalvo);
        assertNotNull(mercadoSalvo.getId());

        assertEquals(
                "Mercadinho JMM",
                mercadoSalvo.getNome()
        );

        assertEquals(
                "12.345.678/0001-90",
                mercadoSalvo.getCnpj()
        );

        assertEquals(
                "Rua Central, 100",
                mercadoSalvo.getEndereco()
        );

        assertEquals(
                "(87) 99999-1111",
                mercadoSalvo.getTelefone()
        );

        assertEquals(
                "contato@jmm.com",
                mercadoSalvo.getEmail()
        );

        assertEquals(
                "08:00 às 20:00",
                mercadoSalvo.getHorarioFuncionamento()
        );
    }

    // Verifica se é possível procurar um Mercado utilizando seu ID
    @Test
    void testarProcurarMercadoId()
            throws EntidadeNaoEncontradaException {

        // Criação do Mercado para teste
        Mercado mercado = new Mercado();
        mercado.setNome("Mercado Central");
        mercado.setCnpj("23.456.789/0001-01");
        mercado.setEndereco("Avenida Principal, 200");
        mercado.setTelefone("(81) 98888-2222");
        mercado.setEmail("central@mercado.com");
        mercado.setHorarioFuncionamento("07:00 às 22:00");

        // Salva o Mercado para gerar um ID
        Mercado mercadoSalvo =
                cadastroMercado.salvarMercado(mercado);

        // Procura o Mercado pelo ID
        Mercado mercadoEncontrado =
                cadastroMercado.procurarMercadoId(
                        mercadoSalvo.getId()
                );

        // Verifica se o Mercado encontrado possui os dados esperados
        assertNotNull(mercadoEncontrado);

        assertEquals(
                mercadoSalvo.getId(),
                mercadoEncontrado.getId()
        );

        assertEquals(
                "Mercado Central",
                mercadoEncontrado.getNome()
        );

        assertEquals(
                "23.456.789/0001-01",
                mercadoEncontrado.getCnpj()
        );

        assertEquals(
                "Avenida Principal, 200",
                mercadoEncontrado.getEndereco()
        );

        assertEquals(
                "(81) 98888-2222",
                mercadoEncontrado.getTelefone()
        );

        assertEquals(
                "central@mercado.com",
                mercadoEncontrado.getEmail()
        );

        assertEquals(
                "07:00 às 22:00",
                mercadoEncontrado.getHorarioFuncionamento()
        );
    }

    // Verifica se é possível listar os Mercados cadastrados
    @Test
    void testarListarMercados() {

        // Criação dos Mercados para teste
        Mercado mercado1 = new Mercado();
        mercado1.setNome("Mercado A");
        mercado1.setCnpj("34.567.890/0001-12");
        mercado1.setEndereco("Rua A, 10");
        mercado1.setTelefone("(87) 97777-3333");
        mercado1.setEmail("mercadoa@email.com");
        mercado1.setHorarioFuncionamento("08:00 às 18:00");

        Mercado mercado2 = new Mercado();
        mercado2.setNome("Mercado B");
        mercado2.setCnpj("45.678.901/0001-23");
        mercado2.setEndereco("Rua B, 20");
        mercado2.setTelefone("(87) 96666-4444");
        mercado2.setEmail("mercadob@email.com");
        mercado2.setHorarioFuncionamento("08:00 às 21:00");

        // Salva os Mercados
        cadastroMercado.salvarMercado(mercado1);
        cadastroMercado.salvarMercado(mercado2);

        // Lista os Mercados
        List<Mercado> mercados =
                cadastroMercado.listarMercados();

        // Verifica se os Mercados foram encontrados
        assertNotNull(mercados);
        assertTrue(mercados.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Mercado
    @Test
    void testarVerificarExistenciaMercadoId() {

        // Criação do Mercado para teste
        Mercado mercado = new Mercado();
        mercado.setNome("Mercado Sul");
        mercado.setCnpj("56.789.012/0001-34");
        mercado.setEndereco("Rua Sul, 300");
        mercado.setTelefone("(81) 95555-5555");
        mercado.setEmail("sul@mercado.com");
        mercado.setHorarioFuncionamento("09:00 às 19:00");

        // Salva o Mercado
        Mercado mercadoSalvo =
                cadastroMercado.salvarMercado(mercado);

        // Verifica se o Mercado existe pelo ID
        boolean existe =
                cadastroMercado.verificarExistenciaMercadoId(
                        mercadoSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Mercado pode ser removido utilizando seu ID
    @Test
    void testarRemoverMercadoId()
            throws EntidadeNaoEncontradaException {

        // Criação do Mercado para teste
        Mercado mercado = new Mercado();
        mercado.setNome("Mercado Temporário");
        mercado.setCnpj("67.890.123/0001-45");
        mercado.setEndereco("Rua Temporária, 400");
        mercado.setTelefone("(81) 94444-6666");
        mercado.setEmail("temporario@mercado.com");
        mercado.setHorarioFuncionamento("08:00 às 17:00");

        // Salva o Mercado
        Mercado mercadoSalvo =
                cadastroMercado.salvarMercado(mercado);

        Long id = mercadoSalvo.getId();

        // Remove o Mercado pelo ID
        cadastroMercado.removerMercadoId(id);

        // Verifica se o Mercado foi removido
        assertFalse(
                cadastroMercado.verificarExistenciaMercadoId(id)
        );
    }

    // Verifica se procurar um Mercado inexistente lança a exceção esperada
    @Test
    void testarProcurarMercadoInexistente() {

        // Define um ID que não existe
        Long idInexistente = 999999L;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroMercado.procurarMercadoId(
                        idInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}