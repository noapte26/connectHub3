package ChatSystemBackend;
import Account.UserAccount;

public class Message {
    private  String chatId;
    private  UserAccount sender;
    private  String messageText;
    private  String imagePath;

    // Constructor

    public Message() {
    }

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
