package de.fhg.ivi.ids.broker.messagehandler;

import de.fraunhofer.iais.eis.ResourceUnavailableMessage;
import de.fraunhofer.iais.eis.ResourceUnavailableMessageImpl;
import de.fraunhofer.ids.messaging.handler.message.MessageHandler;
import de.fraunhofer.ids.messaging.handler.message.MessageHandlerException;
import de.fraunhofer.ids.messaging.handler.message.MessagePayload;
import de.fraunhofer.ids.messaging.handler.message.SupportedMessageType;
import de.fraunhofer.ids.messaging.response.MessageResponse;
import org.springframework.stereotype.Component;

@Component
@SupportedMessageType(ResourceUnavailableMessageImpl.class)
public class ResourceUnavailableMessageHandler implements MessageHandler<ResourceUnavailableMessage> {

    @Override
    public MessageResponse handleMessage(ResourceUnavailableMessage queryHeader, MessagePayload payload)
            throws MessageHandlerException {
        return null;
    }
}