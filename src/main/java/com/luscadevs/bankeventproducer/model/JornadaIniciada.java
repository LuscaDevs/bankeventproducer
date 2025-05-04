package com.luscadevs.bankeventproducer.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JornadaIniciada {
    private String idInstancia;
    private String etapaAtual;
}
