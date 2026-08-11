package br.ufape.poo.mercado.service;

import org.springframework.stereotype.Service;

import br.ufape.poo.mercado.repository.ClienteRepository;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
}