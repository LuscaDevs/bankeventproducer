package com.luscadevs.bankeventproducer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.luscadevs.bankeventproducer.model.Instancia;
import com.luscadevs.bankeventproducer.model.JornadaIniciada;
import com.luscadevs.bankeventproducer.service.OrquestradorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class OrquestradorController {
    @Autowired
    private OrquestradorService orquestradorService;

    @GetMapping("/start/{codProduto}")
    public ResponseEntity<JornadaIniciada> start(@PathVariable int codProduto) {
        try {
            JornadaIniciada jornadaIniciada = orquestradorService.iniciarJornada(codProduto);
            return ResponseEntity.status(HttpStatus.CREATED).body(jornadaIniciada);
        } catch (Exception e) {
            System.err.println("Error starting journey: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/next/{idInstancia}")
    public ResponseEntity<JornadaIniciada> next(@PathVariable String idInstancia) {
        try {
            // Implementar lógica para avançar para a próxima etapa da jornada
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            System.err.println("Error moving to next step: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/instancias/{idJornada}")
    public ResponseEntity<List<Instancia>> getInstanciasPorJornada(@PathVariable String idJornada) {
        List<Instancia> instancias = orquestradorService.getInstanciasPorJornada(idJornada);
        if (instancias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(instancias);

        }
        return ResponseEntity.status(HttpStatus.OK).body(instancias);
    }

}
