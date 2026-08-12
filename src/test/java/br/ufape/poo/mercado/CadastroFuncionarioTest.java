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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroFuncionario;
import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Funcionário
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroFuncionarioTest {

    @Autowired
    private InterfaceCadastroFuncionario cadastroFuncionario;

    // Verifica se os dados do Funcionário estão sendo salvos corretamente
    @Test
    void testarSalvarFuncionario() {

        // Criação do Funcionário para teste
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Carlos Silva");
        funcionario.setCpf("123.456.789-00");
        funcionario.setTelefone("(87) 99999-1111");
        funcionario.setEmail("carlos@mercado.com");
        funcionario.setCargo("Caixa");
        funcionario.setSalario(1800.0);
        funcionario.setSenha("senha123");

        // Salva o Funcionário
        Funcionario funcionarioSalvo =
                cadastroFuncionario.salvarFuncionario(funcionario);

        // Verifica se o Funcionário foi salvo corretamente
        assertNotNull(funcionarioSalvo);
        assertNotNull(funcionarioSalvo.getId());

        assertEquals(
                "Carlos Silva",
                funcionarioSalvo.getNome()
        );

        assertEquals(
                "123.456.789-00",
                funcionarioSalvo.getCpf()
        );

        assertEquals(
                "(87) 99999-1111",
                funcionarioSalvo.getTelefone()
        );

        assertEquals(
                "carlos@mercado.com",
                funcionarioSalvo.getEmail()
        );

        assertEquals(
                "Caixa",
                funcionarioSalvo.getCargo()
        );

        assertEquals(
                1800.0,
                funcionarioSalvo.getSalario(),
                0.001
        );

        assertEquals(
                "senha123",
                funcionarioSalvo.getSenha()
        );
    }

    // Verifica se é possível procurar um Funcionário utilizando seu ID
    @Test
    void testarProcurarFuncionarioId()
            throws EntidadeNaoEncontradaException {

        // Criação do Funcionário para teste
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria Souza");
        funcionario.setCpf("987.654.321-00");
        funcionario.setTelefone("(81) 98888-2222");
        funcionario.setEmail("maria@mercado.com");
        funcionario.setCargo("Gerente");
        funcionario.setSalario(3500.0);
        funcionario.setSenha("maria123");

        // Salva o Funcionário para gerar um ID
        Funcionario funcionarioSalvo =
                cadastroFuncionario.salvarFuncionario(funcionario);

        // Procura o Funcionário pelo ID
        Funcionario funcionarioEncontrado =
                cadastroFuncionario.procurarFuncionarioId(
                        funcionarioSalvo.getId()
                );

        // Verifica se o Funcionário encontrado possui os dados esperados
        assertNotNull(funcionarioEncontrado);

        assertEquals(
                funcionarioSalvo.getId(),
                funcionarioEncontrado.getId()
        );

        assertEquals(
                "Maria Souza",
                funcionarioEncontrado.getNome()
        );

        assertEquals(
                "987.654.321-00",
                funcionarioEncontrado.getCpf()
        );

        assertEquals(
                "(81) 98888-2222",
                funcionarioEncontrado.getTelefone()
        );

        assertEquals(
                "maria@mercado.com",
                funcionarioEncontrado.getEmail()
        );

        assertEquals(
                "Gerente",
                funcionarioEncontrado.getCargo()
        );

        assertEquals(
                3500.0,
                funcionarioEncontrado.getSalario(),
                0.001
        );

        assertEquals(
                "maria123",
                funcionarioEncontrado.getSenha()
        );
    }

    // Verifica se é possível listar os Funcionários cadastrados
    @Test
    void testarListarFuncionarios() {

        // Criação dos Funcionários para teste
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Pedro Lima");
        funcionario1.setCpf("111.222.333-44");
        funcionario1.setTelefone("(87) 97777-3333");
        funcionario1.setEmail("pedro@mercado.com");
        funcionario1.setCargo("Estoquista");
        funcionario1.setSalario(1700.0);
        funcionario1.setSenha("pedro123");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setNome("Ana Oliveira");
        funcionario2.setCpf("555.666.777-88");
        funcionario2.setTelefone("(87) 96666-4444");
        funcionario2.setEmail("ana@mercado.com");
        funcionario2.setCargo("Atendente");
        funcionario2.setSalario(1600.0);
        funcionario2.setSenha("ana123");

        // Salva os Funcionários
        cadastroFuncionario.salvarFuncionario(funcionario1);
        cadastroFuncionario.salvarFuncionario(funcionario2);

        // Lista os Funcionários
        List<Funcionario> funcionarios =
                cadastroFuncionario.listarFuncionarios();

        // Verifica se os Funcionários foram encontrados
        assertNotNull(funcionarios);
        assertTrue(funcionarios.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Funcionário
    @Test
    void testarVerificarExistenciaFuncionarioId() {

        // Criação do Funcionário para teste
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Lucas Ferreira");
        funcionario.setCpf("222.333.444-55");
        funcionario.setTelefone("(81) 95555-5555");
        funcionario.setEmail("lucas@mercado.com");
        funcionario.setCargo("Repositor");
        funcionario.setSalario(1650.0);
        funcionario.setSenha("lucas123");

        // Salva o Funcionário
        Funcionario funcionarioSalvo =
                cadastroFuncionario.salvarFuncionario(funcionario);

        // Verifica se o Funcionário existe pelo ID
        boolean existe =
                cadastroFuncionario.verificarExistenciaFuncionarioId(
                        funcionarioSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Funcionário pode ser removido utilizando seu ID
    @Test
    void testarRemoverFuncionarioId()
            throws EntidadeNaoEncontradaException {

        // Criação do Funcionário para teste
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João Santos");
        funcionario.setCpf("333.444.555-66");
        funcionario.setTelefone("(81) 94444-6666");
        funcionario.setEmail("joao@mercado.com");
        funcionario.setCargo("Auxiliar");
        funcionario.setSalario(1500.0);
        funcionario.setSenha("joao123");

        // Salva o Funcionário
        Funcionario funcionarioSalvo =
                cadastroFuncionario.salvarFuncionario(funcionario);

        Integer id = funcionarioSalvo.getId();

        // Remove o Funcionário pelo ID
        cadastroFuncionario.removerFuncionarioId(id);

        // Verifica se o Funcionário foi removido
        assertFalse(
                cadastroFuncionario.verificarExistenciaFuncionarioId(id)
        );
    }

    // Verifica se procurar um Funcionário inexistente lança a exceção esperada
    @Test
    void testarProcurarFuncionarioInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroFuncionario.procurarFuncionarioId(
                        idInexistente
                )
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}