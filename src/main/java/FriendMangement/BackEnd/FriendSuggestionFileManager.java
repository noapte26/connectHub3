package FriendMangement.BackEnd;

import Account.AccountLoad;
import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import UserAccountManagementBackend.*;

import java.io.*;
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
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }

        finally {
            return accounts;// Return an empty list if loading fails
        }
    }

    // Save friend suggestions for a user
    public void saveFriendSuggestions(String userId, ArrayList<UserAccount> SuggestedUsers) {
        String filePath = generateSuggestionFilePath(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            if(SuggestedUsers.isEmpty())
            {
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            for (UserAccount user : SuggestedUsers)
            {
                String profileJson = objectMapper.writeValueAsString(user);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

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

    public ArrayList<UserAccount> provideFriendSuggestions(UserAccount userAccount) {
        BlockingListFileManager blockingListFileManager = new BlockingListFileManager();
        FriendRequestFileManager friendRequestFileManager = new FriendRequestFileManager();
        FriendListFileManager friendListFileManager = new FriendListFileManager();

        ArrayList<UserAccount> Blocked = (ArrayList<UserAccount>) blockingListFileManager.loadBlockingList(userAccount.getUser().getUserId());
        ArrayList<UserAccount> Requests = (ArrayList<UserAccount>) friendRequestFileManager.loadFriendRequests(userAccount.getUser().getUserId());
        ArrayList<UserAccount> Friends = (ArrayList<UserAccount>) friendListFileManager.loadFriendList(userAccount.getUser().getUserId());

        HashSet<String> users = new HashSet<>();
        //Add BlockedList to the set
        for (UserAccount user : Blocked) {
            users.add(user.getUser().getUserId());
        }
        //Add RequestList to the set
        for (UserAccount user : Requests) {
            users.add(user.getUser().getUserId());
            System.out.println(user.getUser().getUserName());
        }
        //Add FriendList to the set
        for (UserAccount user : Friends) {
            users.add(user.getUser().getUserId());
        }
        //adding my account
        users.add(userAccount.getUser().getUserId());

        ArrayList<UserAccount> SuggestedUsers = new ArrayList<>();
        ArrayList<UserAccount> allUsers;
        AccountLoad acc =new AccountLoad();
        allUsers = acc.loadAccounts();
        for (UserAccount user : allUsers) {
            if (!users.contains(user.getUser().getUserId())) {
                SuggestedUsers.add(user);
            }
        }
        this.saveFriendSuggestions(userAccount.getUser().getUserId(),SuggestedUsers);
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
        System.out.println("333");
        FriendRequestFileManager requestFileManager = new FriendRequestFileManager();
        requestFileManager.addFriendRequest(reciever.getUser().getUserId(), sender);
        removeFriendSuggestion(sender.getUser().getUserId(), reciever);
    }
}
