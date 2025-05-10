package com.luscadevs.bankeventproducer.util;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.luscadevs.bankeventproducer.model.Etapa;
import com.luscadevs.bankeventproducer.model.Instancia;
import com.luscadevs.bankeventproducer.model.Jornada;
import com.luscadevs.bankeventproducer.model.Produto;
import com.luscadevs.bankeventproducer.model.StatusInsntaciaEnum;

@Component
public class Helper {
    /**
     * Converte um documento MongoDB para um objeto Jornada.
     *
     * @param document Documento MongoDB.
     * @return Objeto Jornada.
     */
    @SuppressWarnings("unchecked")
    public Jornada convertDocumentToJornada(Document document) {
        Jornada jornada = new Jornada();

        jornada.setId(document.getString("id"));

        // Converte o campo "produto" manualmente para a classe Produto
        Document produtoDocument = document.get("produto", Document.class);
        Produto produto = new Produto();
        produto.setId(produtoDocument.getInteger("id"));
        produto.setNome(produtoDocument.getString("nome"));
        produto.setDescricao(produtoDocument.getString("descricao"));
        jornada.setProduto(produto);

        // Converte o campo "etapa" para uma lista de objetos Etapa
        List<Document> etapasDocument = (List<Document>) document.get("etapa");
        List<Etapa> etapas = etapasDocument.stream().map(etapaDoc -> {
            Etapa etapa = new Etapa();
            etapa.setNome(etapaDoc.getString("nome"));
            etapa.setDescricao(etapaDoc.getString("descricao"));
            etapa.setOrdem(etapaDoc.getInteger("ordem"));
            return etapa;
        }).toList();
        jornada.setEtapas(etapas);

        return jornada;
    }

    /**
     * Converte um objeto Instancia para um documento MongoDB.
     *
     * @param instancia Objeto Instancia.
     * @return Documento MongoDB.
     */
    public Document convertInstanciaToDocument(Instancia instancia) {
        Document document = new Document();

        // Converte os campos da Instancia para o documento
        document.put("idInstancia", instancia.getIdInstancia());
        document.put("idJornada", instancia.getIdJornada());
        document.put("dataCriacao", instancia.getDataCriacao());
        document.put("dataAtualizacao", instancia.getDataAtualizacao());
        document.put("etapaAtual", instancia.getEtapaAtual());
        document.put("idProduto", instancia.getIdProduto());
        document.append("status", instancia.getStatus().name()); // Converte o enum para string

        // Adicione outros campos conforme necessário
        // Exemplo: document.put("campoAdicional", instancia.getCampoAdicional());

        return document;
    }

    /**
     * Converte um documento MongoDB para um objeto Instancia.
     *
     * @param document Documento MongoDB.
     * @return Objeto Instancia.
     */
    public Instancia convertDocumentToInstancia(Document document) {
        Instancia instancia = new Instancia();

        // Converte os campos do documento para a Instancia
        instancia.setIdInstancia(document.getString("idInstancia"));
        instancia.setIdJornada(document.getString("idJornada"));
        instancia.setDataCriacao(document.getDate("dataCriacao"));
        instancia.setDataAtualizacao(document.getDate("dataAtualizacao"));
        instancia.setEtapaAtual(document.getString("etapaAtual"));
        instancia.setIdProduto(document.getInteger("idProduto"));

        // Converte o campo "status" de string para o enum correspondente
        String statusString = document.getString("status");
        if (statusString != null) {
            instancia.setStatus(StatusInsntaciaEnum.valueOf(statusString)); // Usa StatusInsntaciaEnum
        }

        return instancia;
    }

}
