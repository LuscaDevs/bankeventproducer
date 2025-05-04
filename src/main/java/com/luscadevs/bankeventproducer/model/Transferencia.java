package com.luscadevs.bankeventproducer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transferencia extends Transacao {
    private int contaDestino;
    private String descricao;

}
