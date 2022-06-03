package de.fhg.ivi.ids.broker.messagehandler;

import de.fhg.ivi.ids.broker.core.SelfDescriptionPersistenceAndIndexing;
import de.fraunhofer.iais.eis.ConnectorUnavailableMessage;
import de.fraunhofer.iais.eis.ConnectorUnavailableMessageImpl;
import de.fraunhofer.ids.messaging.handler.message.SupportedMessageType;
import de.fraunhofer.ids.messaging.response.MessageResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SupportedMessageType(ConnectorUnavailableMessageImpl.class)
public class ConnectorUnavailableMessageHandler extends AbstractMessageHandler<ConnectorUnavailableMessage> {

    @Autowired
    SelfDescriptionPersistenceAndIndexing connectorIndexing;

    @Override
    @SneakyThrows
    public MessageResponse handleMessage(ConnectorUnavailableMessage queryHeader, String payload) {
        connectorIndexing.unavailable(queryHeader.getIssuerConnector()); // TODO shouldn't this be the affectedConnector?
        return null;
    }
}