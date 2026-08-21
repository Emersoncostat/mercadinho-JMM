package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.LoteDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.LoteDTOResponse;
import br.ufape.poo.mercado.model.Lote;

@Component
public class LoteConversor {

    public Lote requestToEntity(LoteDTORequest dto) {
        return new Lote(
                null,
                dto.categoriaDoProduto(),
                dto.marcaDoProduto(),
                dto.quantidade(),
                dto.codigo(),
                dto.valorTotalDoLote(),
                dto.validade(),
                dto.fabricacao()
        );
    }

    public LoteDTOResponse entityToResponse(Lote entity) {
        return new LoteDTOResponse(
                entity.getId(),
                entity.getCategoriaDoProduto(),
                entity.getMarcaDoProduto(),
                entity.getQuantidade(),
                entity.getCodigo(),
                entity.getValorTotalDoLote(),
                entity.getValidade(),
                entity.getFabricacao()
        );
    }
}