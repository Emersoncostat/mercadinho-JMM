package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.EstoqueDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.EstoqueDTOResponse;
import br.ufape.poo.mercado.model.Estoque;

@Component
public class EstoqueConversor {

    public Estoque requestToEntity(EstoqueDTORequest dto) {
        return new Estoque(
                dto.quantidadeDisponivel(),
                dto.estoqueMinimo(),
                dto.estoqueMaximo(),
                dto.dataAtualizacao()
        );
    }

    public EstoqueDTOResponse entityToResponse(Estoque entity) {
        return new EstoqueDTOResponse(
                entity.getId(),
                entity.getQuantidadeDisponivel(),
                entity.getEstoqueMinimo(),
                entity.getEstoqueMaximo(),
                entity.getDataAtualizacao()
        );
    }
}