package chatsBackEnd;

import Account.UserAccount;

public class TheBuilder {
    private String chatId;
    private UserAccount sender;
    private String messageText = ""; // Default value
    private String imagePath = ""; // Default value

    public TheBuilder(String chatId, UserAccount sender) {
        this.chatId = chatId;
        this.sender = sender;
    }

    public TheBuilder setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public TheBuilder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Message build() { // Ensure "Message" matches your class name
        return new Message(chatId, sender, messageText, imagePath);
    }
}
