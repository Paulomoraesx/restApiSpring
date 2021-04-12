package com.primeiro.restapispring.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ComentarioInput {

    @NotBlank
    private String descricao;
}
