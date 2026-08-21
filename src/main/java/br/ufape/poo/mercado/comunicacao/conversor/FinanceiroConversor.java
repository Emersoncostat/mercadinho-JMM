package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.FinanceiroDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.FinanceiroDTOResponse;
import br.ufape.poo.mercado.model.Financeiro;

@Component
public class FinanceiroConversor {

    public Financeiro requestToEntity(FinanceiroDTORequest dto) {
        return new Financeiro(
                dto.receita(),
                dto.despesa(),
                dto.lucro(),
                dto.dataRegistro()
        );
    }

    public FinanceiroDTOResponse entityToResponse(Financeiro entity) {
        return new FinanceiroDTOResponse(
                entity.getId(),
                entity.getReceita(),
                entity.getDespesa(),
                entity.getLucro(),
                entity.getDataRegistro()
        );
    }
}