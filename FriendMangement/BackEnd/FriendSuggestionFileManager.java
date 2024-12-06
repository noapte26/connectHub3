package FriendMangement.BackEnd;

import Account.AccountLoad;
import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import UserAccountManagementBackend.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FriendSuggestionFileManager {

    private final String baseDirectory;
    private final ObjectMapper objectMapper;

    public FriendSuggestionFileManager() {
        this.baseDirectory = "friend_suggestions";
        this.objectMapper = new ObjectMapper();
        createBaseDirectory();
    }

    // Ensure the base directory exists
    private void createBaseDirectory() {
        File dir = new File(baseDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Generate the file path for a user's friend suggestions
    private String generateSuggestionFilePath(String userId) {
        return baseDirectory + File.separator + "user_" + userId + "_friend_suggestions.json";
    }

    // Load friend suggestions for a user
    public ArrayList<UserAccount> loadFriendSuggestions(String userId) {
        // String filePath = generateSuggestionFilePath(userId);
        ArrayList<UserAccount> accounts = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UserAccount account = objectMapper.readValue(line, UserAccount.class);
                accounts.add(account);
                return accounts;
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }

        return new ArrayList<>(); // Return an empty list if loading fails
    }

    // Save friend suggestions for a user
    public void saveFriendSuggestions(String userId, ArrayList<UserAccount> SuggestedUsers) {
        String filePath = generateSuggestionFilePath(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        try {
            objectMapper.writeValue(new File(filePath), SuggestedUsers);
            System.out.println("Friend suggestions saved for user ID: " + userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a suggestion for a user
    public void addFriendSuggestion(String userId, UserAccount suggestion) {
        ArrayList<UserAccount> suggestions = (ArrayList<UserAccount>) loadFriendSuggestions(userId);

        if (!suggestions.contains(suggestion)) { // Avoid duplicates
            suggestions.add(suggestion);
            saveFriendSuggestions(userId, suggestions);

        }
    }

    public ArrayList<UserAccount> provideFriendSuggestions(String userId) {
        BlockingListFileManager blockingListFileManager = new BlockingListFileManager();
        FriendRequestFileManager friendRequestFileManager = new FriendRequestFileManager();
        FriendListFileManager friendListFileManager = new FriendListFileManager();

        ArrayList<UserAccount> Blocked = (ArrayList<UserAccount>) blockingListFileManager.loadBlockingList(userId);
        ArrayList<UserAccount> Requests = (ArrayList<UserAccount>) friendRequestFileManager.loadFriendRequests(userId);
        ArrayList<UserAccount> Friends = (ArrayList<UserAccount>) friendListFileManager.loadFriendList(userId);

        HashSet<User> users = new HashSet<>();
        //Add BlockedList to the set
        for (UserAccount user : Blocked) {
            users.add(user.getUser());
        }
        //Add RequestList to the set
        for (UserAccount user : Requests) {
            users.add(user.getUser());
        }
        //Add FriendList to the set
        for (UserAccount user : Friends) {
            users.add(user.getUser());
        }

        ArrayList<UserAccount> SuggestedUsers = new ArrayList<>();
        HashSet<UserAccount> allUsers;
        AccountLoad acc = null;
        allUsers = acc.loadAccounts();
        for (UserAccount user : allUsers) {
            if (!users.contains(user)) {
                SuggestedUsers.add(user);
            }
        }
        return SuggestedUsers;
    }

    // Remove a suggestion for a user
    public void removeFriendSuggestion(String userId, UserAccount suggestion) {
        ArrayList<UserAccount> suggestions = (ArrayList<UserAccount>) loadFriendSuggestions(userId);
        if (suggestions.remove(suggestion)) {
            saveFriendSuggestions(userId, suggestions);
        }
    }

    public void addFriend(UserAccount sender, UserAccount reciever) {
        FriendRequestFileManager requestFileManager = new FriendRequestFileManager();
        requestFileManager.addFriendRequest(sender.getUser().getUserId(), reciever);
        removeFriendSuggestion(sender.getUser().getUserId(), reciever);
    }
}
