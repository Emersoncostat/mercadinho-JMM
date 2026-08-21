package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.PagamentoDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.PagamentoDTOResponse;
import br.ufape.poo.mercado.model.Pagamento;

@Component
public class PagamentoConversor {

    public Pagamento requestToEntity(PagamentoDTORequest dto) {
        return new Pagamento(
                dto.tipoPagamento(),
                dto.valorPago(),
                dto.dataPagamento(),
                dto.status(),
                dto.troco()
        );
    }

    public PagamentoDTOResponse entityToResponse(Pagamento entity) {
        return new PagamentoDTOResponse(
                entity.getId(),
                entity.getTipoPagamento(),
                entity.getValorPago(),
                entity.getDataPagamento(),
                entity.getStatus(),
                entity.getTroco()
        );
    }
}