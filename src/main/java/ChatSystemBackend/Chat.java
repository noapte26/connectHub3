package chatsBackEnd;
import Account.UserAccount;

public class Chat {
    private final String chatId;
    private final UserAccount user1;
    private final UserAccount user2;

    public Chat(String chatId, UserAccount user1, UserAccount user2) {
        this.chatId = chatId;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getChatId() {
        return chatId;
    }

    public UserAccount getUser1() {
        return user1;
    }

    public UserAccount getUser2() {
        return user2;
    }
}