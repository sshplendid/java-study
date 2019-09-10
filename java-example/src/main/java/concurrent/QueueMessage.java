package concurrent;

public class QueueMessage {
    private Integer messageId;
    private String content;

    public QueueMessage(Integer messageId, String content) {
        this.messageId = messageId;
        this.content = content;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
