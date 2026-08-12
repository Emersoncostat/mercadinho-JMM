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

import br.ufape.poo.mercado.cadastro.InterfaceCadastroCliente;
import br.ufape.poo.mercado.model.Cliente;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;

// Testes responsáveis por verificar as operações realizadas pelo cadastro de Cliente
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CadastroClienteTest {

    @Autowired
    private InterfaceCadastroCliente cadastroCliente;

    // Verifica se os dados do Cliente estão sendo salvos corretamente
    @Test
    void testarSalvarCliente() {

        // Criação do Cliente para teste
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setTelefone("(87) 99999-9999");
        cliente.setEndereco("Rua das Flores, 100");
        cliente.setEmail("joao@email.com");

        // Salva o Cliente
        Cliente clienteSalvo = cadastroCliente.salvarCliente(cliente);

        // Verifica se o Cliente foi salvo corretamente
        assertNotNull(clienteSalvo);
        assertNotNull(clienteSalvo.getId());
        assertEquals("João Silva", clienteSalvo.getNome());
        assertEquals("123.456.789-00", clienteSalvo.getCpf());
        assertEquals("(87) 99999-9999", clienteSalvo.getTelefone());
        assertEquals("Rua das Flores, 100", clienteSalvo.getEndereco());
        assertEquals("joao@email.com", clienteSalvo.getEmail());
    }

    // Verifica se é possível procurar um Cliente utilizando seu ID
    @Test
    void testarProcurarClienteId() throws EntidadeNaoEncontradaException {

        // Criação do Cliente para teste
        Cliente cliente = new Cliente();
        cliente.setNome("Maria Souza");
        cliente.setCpf("987.654.321-00");
        cliente.setTelefone("(81) 98888-8888");
        cliente.setEndereco("Avenida Central, 200");
        cliente.setEmail("maria@email.com");

        // Salva o Cliente para gerar um ID
        Cliente clienteSalvo = cadastroCliente.salvarCliente(cliente);

        // Procura o Cliente pelo ID
        Cliente clienteEncontrado =
                cadastroCliente.procurarClienteId(clienteSalvo.getId());

        // Verifica se o Cliente encontrado possui os dados esperados
        assertNotNull(clienteEncontrado);
        assertEquals(clienteSalvo.getId(), clienteEncontrado.getId());
        assertEquals("Maria Souza", clienteEncontrado.getNome());
        assertEquals("987.654.321-00", clienteEncontrado.getCpf());
        assertEquals("(81) 98888-8888", clienteEncontrado.getTelefone());
        assertEquals("Avenida Central, 200", clienteEncontrado.getEndereco());
        assertEquals("maria@email.com", clienteEncontrado.getEmail());
    }

    // Verifica se é possível listar os Clientes cadastrados
    @Test
    void testarListarClientes() {

        // Criação dos Clientes para teste
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Carlos Lima");
        cliente1.setCpf("111.222.333-44");
        cliente1.setTelefone("(87) 97777-7777");
        cliente1.setEndereco("Rua A, 10");
        cliente1.setEmail("carlos@email.com");

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Ana Oliveira");
        cliente2.setCpf("555.666.777-88");
        cliente2.setTelefone("(87) 96666-6666");
        cliente2.setEndereco("Rua B, 20");
        cliente2.setEmail("ana@email.com");

        // Salva os Clientes
        cadastroCliente.salvarCliente(cliente1);
        cadastroCliente.salvarCliente(cliente2);

        // Lista os Clientes
        List<Cliente> clientes = cadastroCliente.listarClientes();

        // Verifica se os Clientes foram encontrados
        assertNotNull(clientes);
        assertTrue(clientes.size() >= 2);
    }

    // Verifica se o cadastro identifica a existência de um Cliente
    @Test
    void testarVerificarExistenciaClienteId() {

        // Criação do Cliente para teste
        Cliente cliente = new Cliente();
        cliente.setNome("Pedro Santos");
        cliente.setCpf("222.333.444-55");
        cliente.setTelefone("(81) 95555-5555");
        cliente.setEndereco("Rua C, 30");
        cliente.setEmail("pedro@email.com");

        // Salva o Cliente
        Cliente clienteSalvo = cadastroCliente.salvarCliente(cliente);

        // Verifica se o Cliente existe pelo ID
        boolean existe =
                cadastroCliente.verificarExistenciaClienteId(
                        clienteSalvo.getId()
                );

        assertTrue(existe);
    }

    // Verifica se um Cliente pode ser removido utilizando seu ID
    @Test
    void testarRemoverClienteId() throws EntidadeNaoEncontradaException {

        // Criação do Cliente para teste
        Cliente cliente = new Cliente();
        cliente.setNome("Lucas Ferreira");
        cliente.setCpf("333.444.555-66");
        cliente.setTelefone("(81) 94444-4444");
        cliente.setEndereco("Rua D, 40");
        cliente.setEmail("lucas@email.com");

        // Salva o Cliente
        Cliente clienteSalvo = cadastroCliente.salvarCliente(cliente);
        Integer id = clienteSalvo.getId();

        // Remove o Cliente pelo ID
        cadastroCliente.removerClienteId(id);

        // Verifica se o Cliente foi removido
        assertFalse(
                cadastroCliente.verificarExistenciaClienteId(id)
        );
    }

    // Verifica se procurar um Cliente inexistente lança a exceção esperada
    @Test
    void testarProcurarClienteInexistente() {

        // Define um ID que não existe
        Integer idInexistente = 999999;

        // Verifica se a exceção é lançada
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> cadastroCliente.procurarClienteId(idInexistente)
        );

        // Verifica se a exceção foi gerada corretamente
        assertNotNull(exception);
    }
}