package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.CaixaDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.CaixaDTOResponse;
import br.ufape.poo.mercado.model.Caixa;

@Component
public class CaixaConversor {

    public Caixa requestToEntity(CaixaDTORequest dto) {
        Caixa caixa = new Caixa();
        caixa.setSaldoInicial(dto.saldoInicial() != null ? dto.saldoInicial() : 0.0);
        caixa.setSaldoFinal(dto.saldoFinal() != null ? dto.saldoFinal() : 0.0);
        caixa.setDataAbertura(dto.dataAbertura());
        caixa.setDataFechamento(dto.dataFechamento());
        return caixa;
    }

    public CaixaDTOResponse entityToResponse(Caixa entity) {
        return new CaixaDTOResponse(
                entity.getId(),
                entity.getSaldoInicial(),
                entity.getSaldoFinal(),
                entity.getDataAbertura(),
                entity.getDataFechamento()
        );
    }
}