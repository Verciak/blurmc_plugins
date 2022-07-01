package org.pieszku.api.redis.packet.client;

import org.pieszku.api.redis.packet.Packet;
import org.pieszku.api.redis.packet.type.MessageType;
import org.pieszku.api.redis.packet.type.ReceiverType;

public class SendMessagePacket extends Packet {

    private final String message;
    private String nickNameReceiver;
    private String messageTitle;
    private final ReceiverType receiverType;
    private final MessageType  messageType;

    public SendMessagePacket(String message, ReceiverType receiverType, MessageType messageType){
        this.message = message;
        this.receiverType = receiverType;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public ReceiverType getReceiverType() {
        return receiverType;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setNickNameReceiver(String nickNameReceiver) {
        this.nickNameReceiver = nickNameReceiver;
    }

    public String getNickNameReceiver() {
        return nickNameReceiver;
    }

    @Override
    public String
    toString() {
        return "SendMessagePacket{" +
                "message='" + message + '\'' +
                ", nickNameReceiver='" + nickNameReceiver + '\'' +
                ", messageTitle='" + messageTitle + '\'' +
                ", receiverType=" + receiverType +
                ", messageType=" + messageType +
                '}';
    }
}
