package com.vekrest.vekproducer.vekproducer.event;

import com.vekrest.vekproducer.vekproducer.entities.Client;
import com.vekrest.vekproducer.vekproducer.entities.Token;
import com.vekrest.vekproducer.vekproducer.integration.interfaces.VekSecurityIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class ClientEventProducer {
    private static final Logger LOG = LoggerFactory.getLogger(ClientEventProducer.class);

    private final String topic;
    private final KafkaTemplate<String, Client> kafkaTemplate;

    private final boolean veksecurityApiEnabled;
    private final VekSecurityIntegration vekSecurityIntegration;
    private String veksecurityApiUsername;
    private String veksecurityApiPassword;

    public ClientEventProducer(
            KafkaTemplate<String, Client> kafkaTemplate,
            VekSecurityIntegration vekSecurityIntegration,
            @Value("${spring.kafka.topic.client.registered}") String topic,
            @Value("${vekrest.veksecurity.api.enabled}") boolean veksecurityApiEnabled,
            @Value("${vekrest.veksecurity.api.username}") String veksecurityApiUsername,
            @Value("${vekrest.veksecurity.api.password}") String veksecurityApiPassword
    ) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        this.vekSecurityIntegration = vekSecurityIntegration;
        this.veksecurityApiEnabled = veksecurityApiEnabled;
        this.veksecurityApiUsername = veksecurityApiUsername;
        this.veksecurityApiPassword = veksecurityApiPassword;
    }

    @Cacheable(value = "veksecurity-token-cache", key = "username")
    private Token getTokenFromVekSecurityApi(String username, String password) {
        return vekSecurityIntegration.getToken(
                username,
                password
        );
    }

    public void send(Client client) throws ExecutionException, InterruptedException, TimeoutException {
        LOG.info("Produzindo evento de cliente para o Kafka: {}", client.toString());

        if(this.veksecurityApiEnabled){
            LOG.info("Adicionando token de segurança da VekSecurity na mensagem Kafka.");

            final String token = getTokenFromVekSecurityApi(this.veksecurityApiUsername, this.veksecurityApiPassword).token();

            Message<Client> message = MessageBuilder
                    .withPayload(client)
                    .setHeader(KafkaHeaders.TOPIC, topic)
                    .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                    .setHeader("TOKEN", token)
                    .build();

            kafkaTemplate.send(message)
                    .get(5000, TimeUnit.MILLISECONDS);

            return;
        }

        kafkaTemplate.send(topic, UUID.randomUUID().toString(), client)
                .get(5000, TimeUnit.MILLISECONDS);
    }
}