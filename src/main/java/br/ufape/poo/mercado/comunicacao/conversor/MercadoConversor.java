package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.MercadoDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.MercadoDTOResponse;
import br.ufape.poo.mercado.model.Mercado;

@Component
public class MercadoConversor {

    public Mercado requestToEntity(MercadoDTORequest dto) {
        return new Mercado(
                null,
                dto.nome(),
                dto.cnpj(),
                dto.endereco(),
                dto.telefone(),
                dto.email(),
                dto.horarioFuncionamento()
        );
    }

    public MercadoDTOResponse entityToResponse(Mercado entity) {
        return new MercadoDTOResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                entity.getEndereco(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getHorarioFuncionamento()
        );
    }
}