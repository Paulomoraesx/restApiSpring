package com.primeiro.restapispring.api.controller;

import com.primeiro.restapispring.api.model.OrdemServicoInputModel;
import com.primeiro.restapispring.api.model.OrdemServicoModel;
import com.primeiro.restapispring.domain.model.OrdemServico;
import com.primeiro.restapispring.domain.repository.OrdemServicoRepository;
import com.primeiro.restapispring.domain.service.OrdemServicoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService servicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel cadastrar(@Valid @RequestBody OrdemServicoInputModel ordemServicoInputModel) {
        OrdemServico ordemServico = toEntity(ordemServicoInputModel);
        return toModel(servicoService.criar(ordemServico));
    }

    @GetMapping
    public List<OrdemServicoModel> listar() {
        return toCollectionModel(ordemServicoRepository.findAll());
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

        if (ordemServico.isPresent()) {
            OrdemServicoModel ordemServicoModel = modelMapper.map(ordemServico.get(), OrdemServicoModel.class);
            return ResponseEntity.ok(ordemServicoModel);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{ordemServicoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long ordemServicoId){
        servicoService.finalizarOrdemServico(ordemServicoId);
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordemServico) {
        return ordemServico.stream().map(this::toModel)
                .collect(Collectors.toList());
    }
    private OrdemServico toEntity(OrdemServicoInputModel ordemServicoInputModel){
        return modelMapper.map(ordemServicoInputModel,OrdemServico.class);
    }
}
