package br.com.takeaguide.userservice.application.services;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ServiceBusSenderClient senderClient;

    // O namespace do Service Bus e o nome da fila são injetados do application.yml
    public MessageService(@Value("${azure.servicebus.namespace}") String namespace,
                          @Value("${azure.servicebus.queue-name}") String queueName) {
        this.senderClient = new ServiceBusClientBuilder()
            .fullyQualifiedNamespace(namespace + ".servicebus.windows.net")
            .credential(new DefaultAzureCredentialBuilder().build())
            .sender()
            .queueName(queueName)
            .buildClient();
    }

    public void sendMessage(String payload) {
        ServiceBusMessage message = new ServiceBusMessage(payload);
        senderClient.sendMessage(message);
    }
}