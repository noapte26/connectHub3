package ChatSystemBackend;
import Account.UserAccount;

public class Message {
    private final String chatId;
    private final UserAccount sender;
    private final String messageText;
    private final String imagePath;

    // Constructor
    Message(String chatId, UserAccount sender, String messageText, String imagePath) {
        this.chatId = chatId;
        this.sender = sender;
        this.messageText = messageText;
        this.imagePath = imagePath;
    }

    // Getters
    public String getChatId() {
        return chatId;
    }

    public UserAccount getSender() {
        return sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getImagePath() {
        return imagePath;
    }
}
