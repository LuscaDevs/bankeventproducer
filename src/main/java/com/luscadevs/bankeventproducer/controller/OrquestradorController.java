package com.luscadevs.bankeventproducer.controller;

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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class OrquestradorController {
    @Autowired
    private OrquestradorService orquestradorService;

    @GetMapping("/start/{codProduto}")
    public ResponseEntity<Object> start(@PathVariable int codProduto) {
        try {
            JornadaIniciada jornadaIniciada = orquestradorService.iniciarJornada(codProduto);
            return ResponseEntity.status(HttpStatus.CREATED).body(jornadaIniciada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao iniciar jornada: " + e.getMessage());
        }
    }

    @GetMapping("/next/{idInstancia}")
    public ResponseEntity<Object> next(@PathVariable String idInstancia) {
        try {
            JornadaIniciada jornadaIniciada = orquestradorService.proximaEtapa(idInstancia);
            return ResponseEntity.status(HttpStatus.OK).body(jornadaIniciada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao mover para próxima etapa: " + e.getMessage());
        }
    }

    @GetMapping("/back/{idInstancia}")
    public ResponseEntity<Object> back(@PathVariable String idInstancia) {
        try {
            JornadaIniciada jornadaIniciada = orquestradorService.etapaAnterior(idInstancia);
            return ResponseEntity.status(HttpStatus.OK).body(jornadaIniciada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao mover para etapa anterior: " + e.getMessage());
        }
    }

    @PutMapping("/finalizar/{idInstancia}")
    public ResponseEntity<Object> finalizar(@PathVariable String idInstancia) {
        try {
            String response = orquestradorService.finalizarJornada(idInstancia);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao finalizar jornada: " + e.getMessage());
        }
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
