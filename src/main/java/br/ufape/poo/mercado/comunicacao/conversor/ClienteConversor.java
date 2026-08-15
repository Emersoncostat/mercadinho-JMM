package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.ClienteDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.ClienteDTOResponse;
import br.ufape.poo.mercado.model.Cliente;

@Component
public class ClienteConversor {

    public Cliente requestToEntity(ClienteDTORequest dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
        cliente.setEmail(dto.email());
        return cliente;
    }

    public ClienteDTOResponse entityToResponse(Cliente entity) {
        return new ClienteDTOResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getTelefone(),
                entity.getEndereco(),
                entity.getEmail()
        );
    }
}