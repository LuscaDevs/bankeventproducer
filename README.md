# Bank Event Producer

Este projeto é responsável por produzir eventos relacionados a operações bancárias, utilizando uma arquitetura baseada em eventos para comunicação assíncrona.

## Funcionalidades

- Produção de eventos para transações bancárias (depósitos, saques, transferências).
- Integração com sistemas de mensageria (Kafka).
- Configuração flexível para diferentes tipos de eventos e tópicos.
- Logs detalhados para auditoria e monitoramento.

## Tecnologias Utilizadas

- **Linguagem:** [Java](https://www.java.com/)
- **Framework:** [Spring Boot](https://spring.io/projects/spring-boot)
- **Mensageria:** [Apache Kafka](https://kafka.apache.org/)
- **Build Tool:** [Maven](https://maven.apache.org/) ou [Gradle](https://gradle.org/)

## Estrutura do Projeto

```
bankeventproducer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   └── test/
├── pom.xml (ou build.gradle)
└── README.md
```

## Como Executar

1. Clone o repositório:

    ```bash
    git clone https://github.com/seu-usuario/bankeventproducer.git
    cd bankeventproducer
    ```

2. Configure as variáveis de ambiente no arquivo `application.properties`:

    ```properties
    spring.kafka.bootstrap-servers=SEU_SERVIDOR_KAFKA
    spring.rabbitmq.host=SEU_SERVIDOR_RABBITMQ
    ```

3. Compile e execute o projeto:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. Verifique os logs para confirmar o envio de eventos.

## Testes

Execute os testes unitários com:

```bash
mvn test
```

## Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para sua feature:

    ```bash
    git checkout -b minha-feature
    ```

3. Faça commit das suas alterações:

    ```bash
    git commit -m "Adiciona minha feature"
    ```

4. Envie para o repositório remoto:

    ```bash
    git push origin minha-feature
    ```

5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## Contato

- **Autor:** Lucas Santos
- **Email:** <lucas.ds.silva@outlook.com>
- **LinkedIn:** [LinkedIn](https://www.linkedin.com/in/lucas-santos-b49785139/)
