package com.luscadevs.bankeventproducer.service;

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
        instancia.setIdProduto(codProduto);
        instancia.setDataCriacao(new Date());
        instancia.setStatus(StatusInsntaciaEnum.EM_ANDAMENTO);
        instancia.setEtapaAtual(jornadaIniciada.getEtapaAtual());

        Document document = new Document();
        document = helper.convertInstanciaToDocument(instancia);
        System.out.println(document.toJson());

        mongoService.insertDocument("Instancias", document);

        return jornadaIniciada;
    }

    public JornadaIniciada proximaEtapa(String idInstancia) {
        Instancia instancia = mongoService.getInstancia(idInstancia); // Obtém a instância atual
        JornadaIniciada jornadaIniciada = new JornadaIniciada();
        jornadaIniciada.setIdInstancia(instancia.getIdInstancia());

        Jornada jornada = mongoService.getJornada(instancia.getIdProduto()); // Obtém a jornada associada à
                                                                             // instância
        List<Etapa> etapas = jornada.getEtapas(); // Obtém as etapas da jornada

        int indiceAtual = -1;
        for (int i = 0; i < etapas.size(); i++) {
            if (etapas.get(i).getNome().equals(instancia.getEtapaAtual())) {
                indiceAtual = i; // Encontra o índice da etapa atual
                break;
            }
        }

        if (indiceAtual != -1 && indiceAtual < etapas.size() - 1) {
            String proximaEtapa = etapas.get(indiceAtual + 1).getNome(); // Obtém o nome da próxima etapa
            System.out.println("Proxima etapa: " + proximaEtapa);
            instancia.setEtapaAtual(proximaEtapa); // Atualiza a etapa atual na instância
            mongoService.updateInstancia(instancia); // Atualiza a instância no banco de dados
        } else {
            throw new IllegalArgumentException("A instância já está na última etapa"); // Lança uma exceção do tipo 400
        }

        jornadaIniciada.setEtapaAtual(instancia.getEtapaAtual()); // Define a etapa atual na jornada iniciada
        return jornadaIniciada; // Retorna a jornada iniciada com a próxima etapa
    }

    public JornadaIniciada etapaAnterior(String idInstancia) {
        Instancia instancia = mongoService.getInstancia(idInstancia); // Obtém a instância atual
        JornadaIniciada jornadaIniciada = new JornadaIniciada();
        jornadaIniciada.setIdInstancia(instancia.getIdInstancia());

        Jornada jornada = mongoService.getJornada(instancia.getIdProduto()); // Obtém a jornada associada à
                                                                             // instância
        List<Etapa> etapas = jornada.getEtapas(); // Obtém as etapas da jornada

        int indiceAtual = -1;
        for (int i = 0; i < etapas.size(); i++) {
            if (etapas.get(i).getNome().equals(instancia.getEtapaAtual())) {
                indiceAtual = i; // Encontra o índice da etapa atual
                break;
            }
        }

        if (indiceAtual > 0) {
            String etapaAnterior = etapas.get(indiceAtual - 1).getNome(); // Obtém o nome da etapa anterior
            System.out.println("Etapa anterior: " + etapaAnterior);
            instancia.setEtapaAtual(etapaAnterior); // Atualiza a etapa atual na instância
            mongoService.updateInstancia(instancia); // Atualiza a instância no banco de dados
        } else {
            throw new IllegalArgumentException("A instância já está na primeira etapa"); // Lança uma exceção do tipo
                                                                                         // 400
        }

        jornadaIniciada.setEtapaAtual(instancia.getEtapaAtual()); // Define a etapa atual na jornada iniciada
        return jornadaIniciada; // Retorna a jornada iniciada com a etapa anterior
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
