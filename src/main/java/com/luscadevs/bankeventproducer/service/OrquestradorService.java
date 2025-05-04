package com.luscadevs.bankeventproducer.service;

import java.lang.annotation.Documented;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luscadevs.bankeventproducer.model.Etapa;
import com.luscadevs.bankeventproducer.model.Instancia;
import com.luscadevs.bankeventproducer.model.Jornada;
import com.luscadevs.bankeventproducer.model.JornadaIniciada;
import com.luscadevs.bankeventproducer.model.StatusInsntaciaEnum;
import com.luscadevs.bankeventproducer.util.Helper;

@Service
public class OrquestradorService {
    @Autowired
    private MongoService mongoService;

    @Autowired
    private Helper helper;

    public JornadaIniciada iniciarJornada(int codProduto) {
        Jornada jornada = mongoService.getJornada(codProduto); // Obtém a jornada do produto

        JornadaIniciada jornadaIniciada = new JornadaIniciada();
        jornadaIniciada.setIdInstancia(UUID.randomUUID().toString());
        jornadaIniciada.setEtapaAtual(jornada.getEtapas().get(0).getNome()); // Obtém a etapa inicial do produto

        Instancia instancia = new Instancia();
        instancia.setIdInstancia(jornadaIniciada.getIdInstancia());
        instancia.setIdJornada(jornada.getId());
        instancia.setDataCriacao(new Date());
        instancia.setStatus(StatusInsntaciaEnum.EM_ANDAMENTO);

        Document document = new Document();
        document = helper.convertInstanciaToDocument(instancia);
        System.out.println(document.toJson());

        mongoService.insertDocument("Instancias", document);

        return jornadaIniciada;
    }

    public List<Instancia> getInstanciasPorJornada(String idJornada) {
        List<Instancia> instancias = new ArrayList<>();

        List<Document> documentos = mongoService.getInstanciasPorJornada(idJornada); // Obtém os documentos das
                                                                                     // instâncias da jornada
        for (Document documento : documentos) {
            Instancia instancia = helper.convertDocumentToInstancia(documento); // Converte o documento para Instancia
            instancias.add(instancia); // Adiciona a instância à lista
        }

        return instancias;
    }
}
