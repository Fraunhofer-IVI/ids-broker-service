package de.fhg.ivi.ids.broker.messagehandler;

import de.fraunhofer.iais.eis.*;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import de.fraunhofer.ids.messaging.core.config.ConfigContainer;
import de.fraunhofer.ids.messaging.core.daps.ConnectorMissingCertExtensionException;
import de.fraunhofer.ids.messaging.core.daps.DapsConnectionException;
import de.fraunhofer.ids.messaging.core.daps.DapsEmptyResponseException;
import de.fraunhofer.ids.messaging.core.daps.DapsTokenProvider;
import de.fraunhofer.ids.messaging.handler.message.MessageHandler;
import de.fraunhofer.ids.messaging.handler.message.MessageHandlerException;
import de.fraunhofer.ids.messaging.handler.message.MessagePayload;
import de.fraunhofer.ids.messaging.handler.message.SupportedMessageType;
import de.fraunhofer.ids.messaging.response.BodyResponse;
import de.fraunhofer.ids.messaging.response.MessageResponse;
import de.fraunhofer.ids.messaging.util.IdsMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@SupportedMessageType(DescriptionRequestMessageImpl.class)
public class DescriptionRequestMessageHandler extends AbstractMessageHandler<DescriptionRequestMessage> {

    @Override
    public MessageResponse handleMessage(DescriptionRequestMessage queryHeader, String payload) {
        var connector = configContainer.getConnector();
        var connectorId = connector.getId();

        var message = new DescriptionResponseMessageBuilder()
                ._securityToken_(getDat())
                ._correlationMessage_(queryHeader.getId())
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._issuerConnector_(connectorId)
                ._modelVersion_(configContainer.getConnector().getOutboundModelVersion())
                ._senderAgent_(connectorId)
                ._recipientConnector_(List.of(queryHeader.getIssuerConnector()))
                .build();

        return BodyResponse.create(message, serialize(connector));
    }

    private String serialize(Connector connector) {
        try {
            return new Serializer().serialize(connector);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
