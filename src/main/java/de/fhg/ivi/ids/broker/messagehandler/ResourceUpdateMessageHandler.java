package de.fhg.ivi.ids.broker.messagehandler;

import de.fraunhofer.iais.eis.ResourceUpdateMessage;
import de.fraunhofer.iais.eis.ResourceUpdateMessageImpl;
import de.fraunhofer.ids.messaging.handler.message.MessageHandler;
import de.fraunhofer.ids.messaging.handler.message.MessagePayload;
import de.fraunhofer.ids.messaging.handler.message.SupportedMessageType;
import de.fraunhofer.ids.messaging.response.MessageResponse;
import org.springframework.stereotype.Component;

@Component
@SupportedMessageType(ResourceUpdateMessageImpl.class)
public class ResourceUpdateMessageHandler implements MessageHandler<ResourceUpdateMessage> {

    @Override
    public MessageResponse handleMessage(final ResourceUpdateMessage requestMessage,
                                         final MessagePayload messagePayload) {
        System.out.println(requestMessage);
        return null;
    }
}