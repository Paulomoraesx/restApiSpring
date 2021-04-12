package com.primeiro.restapispring.api.controller;

import com.primeiro.restapispring.api.model.ComentarioInput;
import com.primeiro.restapispring.api.model.ComentarioModel;
import com.primeiro.restapispring.domain.exception.EntidadeNaoEncontrada;
import com.primeiro.restapispring.domain.model.Comentario;
import com.primeiro.restapispring.domain.model.OrdemServico;
import com.primeiro.restapispring.domain.repository.OrdemServicoRepository;
import com.primeiro.restapispring.domain.service.OrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;


    @GetMapping
    public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(()-> new EntidadeNaoEncontrada("Ordem de Serviço não encontrada"));
        return toCollectionModel(ordemServico.getComentarios());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioModel adicionar (@PathVariable Long ordemServicoId,
                                      @Valid @RequestBody ComentarioInput comentarioInput){
        Comentario comentario = ordemServicoService.adicionarComentario(ordemServicoId,
                comentarioInput.getDescricao());

        return toModel(comentario);
    }
    private ComentarioModel toModel(Comentario comentario){
        return modelMapper.map(comentario, ComentarioModel.class);
    }

    private List<ComentarioModel> toCollectionModel (List<Comentario> comentarios){
        return comentarios.stream().map(this::toModel).collect(Collectors.toList());
    }

}
