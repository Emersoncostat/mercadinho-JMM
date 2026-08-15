package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.ProdutoDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.ProdutoDTOResponse;
import br.ufape.poo.mercado.model.Produto;

@Component
public class ProdutoConversor {

    public Produto requestToEntity(ProdutoDTORequest dto) {
        return new Produto(
                dto.nome(),
                dto.descricao(),
                dto.marca(),
                dto.preco(),
                dto.codigoBarras(),
                dto.validade()
        );
    }

    public ProdutoDTOResponse entityToResponse(Produto entity) {
        return new ProdutoDTOResponse(
                entity.getCodigo(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getMarca(),
                entity.getPreco(),
                entity.getCodigoBarras(),
                entity.getValidade()
        );
    }
}