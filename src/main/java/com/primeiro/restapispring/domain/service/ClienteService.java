package com.primeiro.restapispring.domain.service;

import com.primeiro.restapispring.domain.exception.NegocioException;
import com.primeiro.restapispring.domain.model.Cliente;
import com.primeiro.restapispring.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
        if(clienteExistente != null && !clienteExistente.equals(cliente)){
            throw new NegocioException("Já existe um cliente cadastrado com esse e-mail.");
        }
        return clienteRepository.save(cliente);
    }
    public void excluir(Long clienteID){
         clienteRepository.deleteById(clienteID);
    }
}
