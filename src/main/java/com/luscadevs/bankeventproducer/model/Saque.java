package com.luscadevs.bankeventproducer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Saque extends Transacao {

    private String local;
}
