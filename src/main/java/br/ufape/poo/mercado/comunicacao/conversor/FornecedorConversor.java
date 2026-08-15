package br.ufape.poo.mercado.comunicacao.conversor;

import org.springframework.stereotype.Component;
import br.ufape.poo.mercado.comunicacao.dto.request.FornecedorDTORequest;
import br.ufape.poo.mercado.comunicacao.dto.response.FornecedorDTOResponse;
import br.ufape.poo.mercado.model.Fornecedor;

@Component
public class FornecedorConversor {

    public Fornecedor requestToEntity(FornecedorDTORequest dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setEmail(dto.email());
        fornecedor.setEndereco(dto.endereco());
        return fornecedor;
    }

    public FornecedorDTOResponse entityToResponse(Fornecedor entity) {
        return new FornecedorDTOResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getEndereco()
        );
    }
}