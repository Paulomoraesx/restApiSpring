package com.primeiro.restapispring.api.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ComentarioModel {

    private Integer id;
    private String descricao;
    private OffsetDateTime dataEnvio;
}
