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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroFornecedor;
import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Fornecedor
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroFornecedorTest {

    @Autowired
    private InterfaceCadastroFornecedor cadastroFornecedor;

    // Verifica se os dados do Fornecedor estão sendo salvos corretamente
    @Test
    void testarSalvarFornecedor() {

        // Criação do Fornecedor para teste
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Distribuidora Central");
        fornecedor.setCnpj("12.345.678/0001-90");
        fornecedor.setTelefone("(87) 99999-1111");
        fornecedor.setEndereco("Rua das Empresas, 100");
        fornecedor.setEmail("contato@central.com");

        // Salva o Fornecedor
        Fornecedor fornecedorSalvo =
                cadastroFornecedor.salvarFornecedor(fornecedor);

        // Verifica se o Fornecedor foi salvo corretamente
        assertNotNull(fornecedorSalvo);
        assertNotNull(fornecedorSalvo.getId());

        assertEquals(
                "Distribuidora Central",
                fornecedorSalvo.getNome()
        );

        assertEquals(
                "12.345.678/0001-90",
                fornecedorSalvo.getCnpj()
        );

        assertEquals(
                "(87) 99999-1111",
                fornecedorSalvo.getTelefone()
        );

        assertEquals(
                "Rua das Empresas, 100",
                fornecedorSalvo.getEndereco()
        );

        assertEquals(
                "contato@central.com",
                fornecedorSalvo.getEmail()
        );
    }

    // Verifica se é possível procurar um Fornecedor utilizando seu ID
    @Test
    void testarProcurarFornecedorId()
            throws EntidadeNaoEncontradaException {

        // Criação do Fornecedor para teste
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor Nordeste");
        fornecedor.setCnpj("23.456.789/0001-01");
        fornecedor.setTelefone("(81) 98888-2222");
        fornecedor.setEndereco("Avenida Nordeste, 200");
        fornecedor.setEmail("nordeste@fornecedor.com");

        // Salva o Fornecedor para gerar um ID
        Fornecedor fornecedorSalvo =
                cadastroFornecedor.salvarFornecedor(fornecedor);

        // Procura o Fornecedor pelo ID
        Fornecedor fornecedorEncontrado =
                cadastroFornecedor.procurarFornecedorId(
                        fornecedorSalvo.getId()
                );

        // Verifica se o Fornecedor encontrado possui os dados esperados
        assertNotNull(fornecedorEncontrado);

        assertEquals(
                fornecedorSalvo.getId(),
                fornecedorEncontrado.getId()
        );

        assertEquals(
                "Fornecedor Nordeste",
                fornecedorEncontrado.getNome()
        );

        assertEquals(
                "23.456.789/0001-01",
                fornecedorEncontrado.getCnpj()
        );

        assertEquals(
                "(81) 98888-2222",
                fornecedorEncontrado.getTelefone()
        );

        assertEquals(
                "Avenida Nordeste, 200",
                fornecedorEncontrado.getEndereco()
        );

        assertEquals(
                "nordeste@fornecedor.com",
                fornecedorEncontrado.getEmail()
        );
    }

    // Verifica se é possível listar os Fornecedores cadastrados
    @Test
    void testarListarFornecedores() {

        // Criação dos Fornecedores para teste
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setNome("Fornecedor A");
        fornecedor1.setCnpj("34.567.890/0001-12");
        fornecedor1.setTelefone("(87) 97777-3333");
        fornecedor1.setEndereco("Rua A, 10");
        fornecedor1.setEmail("fornecedora@email.com");

        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setNome("Fornecedor B");
        fornecedor2.setCnpj("45.678.901/0001-23");
        fornecedor2.setTelefone("(87) 96666-4444");
        fornecedor2.setEndereco("Rua B, 20");
        fornecedor2.setEmail("fornecedorb@email.com");

        // Salva os Fornecedores
        cadastroFornecedor.salvarFornecedor(fornecedor1);
        cadastroFornecedor.salvarFornecedor(fornecedor2);

        // Lista os Fornecedores
        List<Fornecedor> fornecedores =
                cadastroFornecedor.listarFornecedores();

        // Verifica se os Fornecedores foram encontrados
        assertNotNull(fornecedores);
        assertTrue(fornecedores.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Fornecedor
    @Test
    void testarVerificarExistenciaFornecedorId() {

        // Criação do Fornecedor para teste
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor Sul");
        fornecedor.setCnpj("56.789.012/0001-34");
        fornecedor.setTelefone("(81) 95555-5555");
        fornecedor.setEndereco("Rua Sul, 300");
        fornecedor.setEmail("sul@fornecedor.com");

        // Salva o Fornecedor
        Fornecedor fornecedorSalvo =
                cadastroFornecedor.salvarFornecedor(fornecedor);

        // Verifica se o Fornecedor existe pelo ID
        boolean existe =
                cadastroFornecedor.verificarExistenciaFornecedorId(
                        fornecedorSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Fornecedor pode ser removido utilizando seu ID
    @Test
    void testarRemoverFornecedorId()
            throws EntidadeNaoEncontradaException {

        // Criação do Fornecedor para teste
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor Temporário");
        fornecedor.setCnpj("67.890.123/0001-45");
        fornecedor.setTelefone("(81) 94444-6666");
        fornecedor.setEndereco("Rua Temporária, 400");
        fornecedor.setEmail("temporario@fornecedor.com");

        // Salva o Fornecedor
        Fornecedor fornecedorSalvo =
                cadastroFornecedor.salvarFornecedor(fornecedor);

        Integer id = fornecedorSalvo.getId();

        // Remove o Fornecedor pelo ID
        cadastroFornecedor.removerFornecedorId(id);

        // Verifica se o Fornecedor foi removido
        assertFalse(
                cadastroFornecedor.verificarExistenciaFornecedorId(id)
        );
    }

    // Verifica se procurar um Fornecedor inexistente lança a exceção esperada
    @Test
    void testarProcurarFornecedorInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroFornecedor.procurarFornecedorId(
                        idInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}