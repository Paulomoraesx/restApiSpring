package com.primeiro.restapispring.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.primeiro.restapispring.domain.exception.NegocioException;
import com.primeiro.restapispring.domain.util.ValidationGroups.ValidationGroups;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
    @NotNull
    @ManyToOne
    private Cliente cliente;
    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal preco;

    @JsonProperty(access = READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

    @JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataAbertura;

    @JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataFinalizacao;

    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();

    public boolean podeSerFinalizada(){
        return StatusOrdemServico.ABERTA.equals(getStatus());
    }

    public void finalizar() {
        if(!podeSerFinalizada()){
            throw new NegocioException("Ordem de serviço não pode ser finalizada!");
        }
        setStatus(StatusOrdemServico.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }
}
