package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.response.VendaDTOResponse;
import br.ufape.poo.mercado.model.Venda;

@Component
public class VendaConversor {

    public VendaDTOResponse entityToResponse(Venda entity) {
        return new VendaDTOResponse(
                entity.getId(),
                entity.getDataVenda(),
                entity.getValorTotal(),
                entity.getQuantidadeProdutos(),
                entity.getDesconto()
        );
    }
}