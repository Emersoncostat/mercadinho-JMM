package br.ufape.poo.mercado.negocio.excecoes;

public class EntidadeNaoEncontradaException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String mensagem;

    public EntidadeNaoEncontradaException(String mensagem) {
        super("Não existe no sistema a entidade informada: " + mensagem);
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return this.mensagem;
    }
}