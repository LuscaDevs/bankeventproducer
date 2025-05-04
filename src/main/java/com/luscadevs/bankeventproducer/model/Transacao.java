package com.luscadevs.bankeventproducer.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Transacao {
    private UUID id;
    private int idConta;
    private BigDecimal valor;
    private Instant data;
}
