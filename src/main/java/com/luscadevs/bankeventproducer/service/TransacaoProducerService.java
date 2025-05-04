package com.luscadevs.bankeventproducer.service;

import com.luscadevs.bankeventproducer.model.Transacao;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransacaoProducerService {

    private final KafkaTemplate<String, Transacao> kafkaTemplate;

    public TransacaoProducerService(KafkaTemplate<String, Transacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransacaoEvent(Transacao transacao) {
        try {
            // Envia para o tópico 'transaction-events'
            kafkaTemplate.send("transaction-events", transacao);
        } catch (Exception e) {
            System.err.println("Error sending transaction event: " + e.getMessage());
        }

    }
}
