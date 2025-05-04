package com.luscadevs.bankeventproducer.controller;

import com.luscadevs.bankeventproducer.model.Deposito;
import com.luscadevs.bankeventproducer.service.TransacaoProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RestController
public class TransacaoController {

    private final TransacaoProducerService producerService;

    public TransacaoController(TransacaoProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/simulate-deposit")
    public String simulateDeposit() {
        Deposito deposito = new Deposito();
        deposito.setId(UUID.randomUUID());
        deposito.setIdConta(123);
        deposito.setValor(new BigDecimal("1000.00"));
        deposito.setData(Instant.now());

        producerService.sendTransacaoEvent(deposito);
        return "Deposit event sent!";
    }
}
