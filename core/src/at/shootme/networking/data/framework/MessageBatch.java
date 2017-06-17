package at.shootme.networking.data.framework;

import java.util.List;

public class MessageBatch {

    private List<?> messages;

    public List<?> getMessages() {
        return messages;
    }

    public void setMessages(List<?> messages) {
        this.messages = messages;
    }

    public static MessageBatch create(List<?> messages){
        MessageBatch messageBatch = new MessageBatch();
        messageBatch.setMessages(messages);
        return messageBatch;
    }
}
