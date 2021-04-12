package com.primeiro.restapispring.domain.service;

import com.primeiro.restapispring.domain.exception.EntidadeNaoEncontrada;
import com.primeiro.restapispring.domain.exception.NegocioException;
import com.primeiro.restapispring.domain.model.Cliente;
import com.primeiro.restapispring.domain.model.Comentario;
import com.primeiro.restapispring.domain.model.OrdemServico;
import com.primeiro.restapispring.domain.model.StatusOrdemServico;
import com.primeiro.restapispring.domain.repository.ClienteRepository;
import com.primeiro.restapispring.domain.repository.ComentarioRepository;
import com.primeiro.restapispring.domain.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));
        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());
        return ordemServicoRepository.save(ordemServico);
    }


    private OrdemServico buscar(Long ordemServicoId, String s) {
        return ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontrada(s));
    }


    public Comentario adicionarComentario(Long ordemServicoId, String descricaoComentario) {
        OrdemServico ordemServico = buscar(ordemServicoId, "Ordem de serviço não encontrada");
        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricaoComentario);
        comentario.setOrdemServico(ordemServico);

        return comentarioRepository.save(comentario);
    }

    public void finalizarOrdemServico(Long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId, "Ordem de Serviço não encontrada");
        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }
}
