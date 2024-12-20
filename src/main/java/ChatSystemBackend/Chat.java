package ChatSystemBackend;
import Account.UserAccount;

import java.util.UUID;

public class Chat {
    private  String chatId;
    private  UserAccount user1;
    private  UserAccount user2;

    public Chat() {
    }

    public Chat(UserAccount user1, UserAccount user2) {
        this.chatId =  UUID.randomUUID().toString() ;;
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