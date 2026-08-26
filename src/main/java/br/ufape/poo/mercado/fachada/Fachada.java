package br.ufape.poo.mercado.fachada;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufape.poo.mercado.cadastro.InterfaceCadastroCaixa;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroCliente;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroEstoque;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroFinanceiro;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroFornecedor;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroFuncionario;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroLote;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroMercado;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroPagamento;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroProduto;
import br.ufape.poo.mercado.cadastro.InterfaceCadastroVenda;

import br.ufape.poo.mercado.model.Caixa;
import br.ufape.poo.mercado.model.Cliente;
import br.ufape.poo.mercado.model.Estoque;
import br.ufape.poo.mercado.model.Financeiro;
import br.ufape.poo.mercado.model.Fornecedor;
import br.ufape.poo.mercado.model.Funcionario;
import br.ufape.poo.mercado.model.Lote;
import br.ufape.poo.mercado.model.Mercado;
import br.ufape.poo.mercado.model.Pagamento;
import br.ufape.poo.mercado.model.Produto;
import br.ufape.poo.mercado.model.Venda;
import br.ufape.poo.mercado.negocio.excecoes.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;

@Service
@Component
public class Fachada {

    @Autowired
    private InterfaceCadastroCaixa cadastroCaixa;

    @Autowired
    private InterfaceCadastroCliente cadastroCliente;

    @Autowired
    private InterfaceCadastroEstoque cadastroEstoque;

    @Autowired
    private InterfaceCadastroFinanceiro cadastroFinanceiro;

    @Autowired
    private InterfaceCadastroFornecedor cadastroFornecedor;

    @Autowired
    private InterfaceCadastroFuncionario cadastroFuncionario;

    @Autowired
    private InterfaceCadastroLote cadastroLote;

    @Autowired
    private InterfaceCadastroMercado cadastroMercado;

    @Autowired
    private InterfaceCadastroPagamento cadastroPagamento;

    @Autowired
    private InterfaceCadastroProduto cadastroProduto;

    @Autowired
    private InterfaceCadastroVenda cadastroVenda;

    // O construtor antigo com "new" foi removido para permitir a injeção do Spring

    // Caixa
    public Caixa salvarCaixa(Caixa caixa) {
        return cadastroCaixa.salvarCaixa(caixa);
    }

    public Caixa procurarCaixaId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroCaixa.procurarCaixaId(id);
    }

    public List<Caixa> listarCaixas() {
        return cadastroCaixa.listarCaixas();
    }

    public boolean verificarExistenciaCaixaId(Integer id) {
        return cadastroCaixa.verificarExistenciaCaixaId(id);
    }

    public void removerCaixaId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroCaixa.removerCaixaId(id);
    }

    // Cliente
    public Cliente salvarCliente(Cliente cliente) {
        return cadastroCliente.salvarCliente(cliente);
    }
    public Cliente atualizarCliente(Integer id, Cliente cliente) throws EntidadeNaoEncontradaException {
        return cadastroCliente.atualizarCliente(id, cliente);
    }
    public Cliente procurarClienteId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroCliente.procurarClienteId(id);
    }

    public List<Cliente> listarClientes() {
        return cadastroCliente.listarClientes();
    }

    public boolean verificarExistenciaClienteId(Integer id) {
        return cadastroCliente.verificarExistenciaClienteId(id);
    }

    public void removerClienteId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroCliente.removerClienteId(id);
    }

    // Estoque
    public Estoque salvarEstoque(Estoque estoque) {
        return cadastroEstoque.salvarEstoque(estoque);
    }

    public Estoque procurarEstoqueId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroEstoque.procurarEstoqueId(id);
    }

    public List<Estoque> listarEstoques() {
        return cadastroEstoque.listarEstoques();
    }

    public boolean verificarExistenciaEstoqueId(Integer id) {
        return cadastroEstoque.verificarExistenciaEstoqueId(id);
    }

    public void removerEstoqueId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroEstoque.removerEstoqueId(id);
    }

    // Financeiro
    public Financeiro salvarFinanceiro(Financeiro financeiro) {
        return cadastroFinanceiro.salvarFinanceiro(financeiro);
    }

    public Financeiro procurarFinanceiroId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroFinanceiro.procurarFinanceiroId(id);
    }

    public List<Financeiro> listarFinanceiros() {
        return cadastroFinanceiro.listarFinanceiros();
    }

    public boolean verificarExistenciaFinanceiroId(Integer id) {
        return cadastroFinanceiro.verificarExistenciaFinanceiroId(id);
    }

    public void removerFinanceiroId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroFinanceiro.removerFinanceiroId(id);
    }

    // Fornecedor
    public Fornecedor salvarFornecedor(Fornecedor fornecedor) {
        return cadastroFornecedor.salvarFornecedor(fornecedor);
    }

    public Fornecedor procurarFornecedorId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroFornecedor.procurarFornecedorId(id);
    }

    public List<Fornecedor> listarFornecedores() {
        return cadastroFornecedor.listarFornecedores();
    }

    public boolean verificarExistenciaFornecedorId(Integer id) {
        return cadastroFornecedor.verificarExistenciaFornecedorId(id);
    }

    public void removerFornecedorId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroFornecedor.removerFornecedorId(id);
    }

    // Funcionario
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return cadastroFuncionario.salvarFuncionario(funcionario);
    }

    public Funcionario procurarFuncionarioId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroFuncionario.procurarFuncionarioId(id);
    }

    public List<Funcionario> listarFuncionarios() {
        return cadastroFuncionario.listarFuncionarios();
    }

    public boolean verificarExistenciaFuncionarioId(Integer id) {
        return cadastroFuncionario.verificarExistenciaFuncionarioId(id);
    }

    public void removerFuncionarioId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroFuncionario.removerFuncionarioId(id);
    }

    // Lote
    public Lote salvarLote(Lote lote) {
        return cadastroLote.salvarLote(lote);
    }

    public Lote procurarLoteId(Long id) throws EntidadeNaoEncontradaException {
        return cadastroLote.procurarLoteId(id);
    }

    public List<Lote> listarLotes() {
        return cadastroLote.listarLotes();
    }

    public boolean verificarExistenciaLoteId(Long id) {
        return cadastroLote.verificarExistenciaLoteId(id);
    }

    public void removerLoteId(Long id) throws EntidadeNaoEncontradaException {
        cadastroLote.removerLoteId(id);
    }

    // Mercado
    public Mercado salvarMercado(Mercado mercado) {
        return cadastroMercado.salvarMercado(mercado);
    }

    public Mercado procurarMercadoId(Long id) throws EntidadeNaoEncontradaException {
        return cadastroMercado.procurarMercadoId(id);
    }

    public List<Mercado> listarMercados() {
        return cadastroMercado.listarMercados();
    }

    public boolean verificarExistenciaMercadoId(Long id) {
        return cadastroMercado.verificarExistenciaMercadoId(id);
    }

    public void removerMercadoId(Long id) throws EntidadeNaoEncontradaException {
        cadastroMercado.removerMercadoId(id);
    }

    // Pagamento
    public Pagamento salvarPagamento(Pagamento pagamento) {
        return cadastroPagamento.salvarPagamento(pagamento);
    }

    public Pagamento procurarPagamentoId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroPagamento.procurarPagamentoId(id);
    }

    public List<Pagamento> listarPagamentos() {
        return cadastroPagamento.listarPagamentos();
    }

    public boolean verificarExistenciaPagamentoId(Integer id) {
        return cadastroPagamento.verificarExistenciaPagamentoId(id);
    }

    public void removerPagamentoId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroPagamento.removerPagamentoId(id);
    }

    // Produto
    public Produto salvarProduto(Produto produto) {
        return cadastroProduto.salvarProduto(produto);
    }

    public Produto procurarProdutoId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroProduto.procurarProdutoId(id);
    }

    public List<Produto> listarProdutos() {
        return cadastroProduto.listarProdutos();
    }

    public boolean verificarExistenciaProdutoId(Integer id) {
        return cadastroProduto.verificarExistenciaProdutoId(id);
    }

    public void removerProdutoId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroProduto.removerProdutoId(id);
    }

    // Venda
    public Venda salvarVenda(Venda venda) {
        return cadastroVenda.salvarVenda(venda);
    }

    public Venda procurarVendaId(Integer id) throws EntidadeNaoEncontradaException {
        return cadastroVenda.procurarVendaId(id);
    }

    public List<Venda> listarVendas() {
        return cadastroVenda.listarVendas();
    }

    public boolean verificarExistenciaVendaId(Integer id) {
        return cadastroVenda.verificarExistenciaVendaId(id);
    }

    public void removerVendaId(Integer id) throws EntidadeNaoEncontradaException {
        cadastroVenda.removerVendaId(id);
    }

    public Venda realizarVendaProduto(Integer idProduto, Integer quantidade, Double desconto) throws EntidadeNaoEncontradaException {

        Produto produto = cadastroProduto.procurarProdutoId(idProduto);


        Venda novaVenda = new Venda("12/08/2026", 0.0, 0, desconto, produto);


        novaVenda.setValorTotal(0.0);
        novaVenda.setQuantidadeProdutos(0);


        novaVenda.adicionarProduto(produto, quantidade);
        novaVenda.finalizarVenda();

        return cadastroVenda.salvarVenda(novaVenda);
    }

}