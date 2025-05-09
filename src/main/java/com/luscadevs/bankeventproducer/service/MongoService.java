package com.luscadevs.bankeventproducer.service;

import com.luscadevs.bankeventproducer.model.Instancia;
import com.luscadevs.bankeventproducer.model.Jornada;
import com.luscadevs.bankeventproducer.util.Helper;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoService {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    @Autowired
    private Helper helper;

    public MongoService() {
        // Configura o MongoClient com uuidRepresentation
        this.mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .uuidRepresentation(UuidRepresentation.STANDARD) // Define a representação de UUID
                        .build());

        this.database = mongoClient.getDatabase("Orquestrador");
    }

    /**
     * Retorna uma coleção específica do banco de dados.
     *
     * @param collectionName Nome da coleção.
     * @return MongoCollection<Document>
     */
    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    /**
     * Insere um documento em uma coleção.
     *
     * @param collectionName Nome da coleção.
     * @param document       Documento a ser inserido.
     */
    public void insertDocument(String collectionName, Document document) {
        try {
            getCollection(collectionName).insertOne(document);
            System.out.println("Documento inserido com sucesso na coleção: " + collectionName);
        } catch (Exception e) {
            System.err.println("Erro ao inserir documento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtém uma jornada completa de um produto na coleção de Jornadas.
     *
     * @param productId ID do produto.
     * @return Objeto Jornada representando a jornada completa, ou null se não
     *         encontrado.
     */
    public Jornada getJornada(int productId) {
        MongoCollection<Document> collection = getCollection("Jornadas");

        // Filtra pelo código do produto (produto.id)
        Document document = collection.find(new Document("produto.id", productId)).first();

        if (document != null) {
            // Converte o documento MongoDB para a classe Jornada
            return helper.convertDocumentToJornada(document);
        }

        return null; // Retorna null se o documento não for encontrado
    }

    /**
     * Obtém uma instância específica pelo ID.
     *
     * @param instanciaId ID da instância.
     * @return Objeto Instancia representando a instância, ou null se não
     *         encontrado.
     */
    public Instancia getInstancia(String instanciaId) {
        MongoCollection<Document> collection = getCollection("Instancias");

        // Filtra pelo ID da instância
        Document document = collection.find(new Document("idInstancia", instanciaId)).first();
        if (document == null) {
            throw new IllegalArgumentException("Instância não encontrada: " + instanciaId);
        }
        return helper.convertDocumentToInstancia(document); // Retorna o documento encontrado

    }

    public void updateInstancia(Instancia instancia) {
        MongoCollection<Document> collection = getCollection("Instancias");

        // Converte a instância para um documento MongoDB
        Document document = helper.convertInstanciaToDocument(instancia);

        try {
            // Atualiza o documento na coleção com base no ID da instância
            collection.replaceOne(new Document("idInstancia", instancia.getIdInstancia()), document);
            System.out.println("Instância atualizada com sucesso: " + instancia.getIdInstancia());
        } catch (Exception e) {
            System.err.println("Erro ao atualizar instância: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtém uma lista de instâncias para uma jornada específica.
     *
     * @param jornadaId ID da jornada.
     * @return Lista de instâncias associadas à jornada, ou uma lista vazia se não
     *         encontrado.
     */
    public List<Document> getInstanciasPorJornada(String jornadaId) {
        MongoCollection<Document> collection = getCollection("Instancias");

        try {
            List<Document> instancias = collection.find(new Document("idJornada", jornadaId)).into(new ArrayList<>());
            return instancias;
        } catch (Exception e) {
            System.err.println("Erro ao obter instâncias: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna uma lista vazia em caso de erro
        }
        // Filtra pelo ID da jornada

    }

    /**
     * Fecha a conexão com o MongoDB.
     */
    public void close() {
        mongoClient.close();
    }
}