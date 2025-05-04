package com.luscadevs.bankeventproducer.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jornada {
    private String id;
    private Produto produto;
    private boolean ativa;
    private List<Etapa> etapas;
}
