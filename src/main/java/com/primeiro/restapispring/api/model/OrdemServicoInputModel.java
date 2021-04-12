package com.primeiro.restapispring.api.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrdemServicoInputModel {

    @NotBlank
    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal preco;

    @Valid
    @NotNull
    private ClienteIdInput cliente;
}
