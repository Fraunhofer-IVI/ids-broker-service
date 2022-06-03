package de.fhg.ivi.ids.broker.messagehandler;

import de.fhg.ivi.ids.broker.core.SelfDescriptionPersistenceAndIndexing;
import de.fraunhofer.iais.eis.ConnectorUpdateMessage;
import de.fraunhofer.iais.eis.ConnectorUpdateMessageImpl;
import de.fraunhofer.iais.eis.InfrastructureComponent;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import de.fraunhofer.ids.messaging.handler.message.SupportedMessageType;
import de.fraunhofer.ids.messaging.response.MessageResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SupportedMessageType(ConnectorUpdateMessageImpl.class)
public class ConnectorUpdateMessageHandler extends AbstractMessageHandler<ConnectorUpdateMessage> {

    @Autowired
    SelfDescriptionPersistenceAndIndexing connectorIndexing;

    @Autowired
    Serializer serializer;

    @Override
    @SneakyThrows
    public MessageResponse handleMessage(ConnectorUpdateMessage requestMessage, String messagePayload) {
        var connector = serializer.deserialize(messagePayload, InfrastructureComponent.class);
        connectorIndexing.updated(connector);
        return messageProcessed(requestMessage);
    }
}