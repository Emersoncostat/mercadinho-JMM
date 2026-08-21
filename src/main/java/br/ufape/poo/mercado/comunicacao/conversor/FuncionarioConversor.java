package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.FuncionarioDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.FuncionarioDTOResponse;
import br.ufape.poo.mercado.model.Funcionario;

@Component
public class FuncionarioConversor {

    public Funcionario requestToEntity(FuncionarioDTORequest dto) {
        return new Funcionario(
                dto.nome(),
                dto.cpf(),
                dto.telefone(),
                dto.email(),
                dto.cargo(),
                dto.salario(),
                dto.senha()
        );
    }

    public FuncionarioDTOResponse entityToResponse(Funcionario entity) {
        return new FuncionarioDTOResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getCargo(),
                entity.getSalario()
        );
    }
}