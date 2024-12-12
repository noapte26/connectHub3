package FriendMangement.BackEnd;

import Account.UserAccount;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FriendRequestFileManager {
    private  String baseDirectory;
    private final ObjectMapper objectMapper = new ObjectMapper();  // For JSON operations

    public FriendRequestFileManager() {
        this.baseDirectory = "friend_requests";
        createBaseDirectory();
    }

    // Create the base directory if it doesn't exist
    private void createBaseDirectory() {
        File dir = new File(baseDirectory);
        if (!dir.exists()) {
            dir.mkdirs();  // Create the directory and any necessary parent directories
        }
    }

    // Generate a JSON file path for a user's friend requests using their user ID
    public String generateFriendRequestFilePath(String userId) {
        return baseDirectory + File.separator + "user_" + userId + "_friend_requests.json";
    }

    // Create a JSON file for storing friend requests for a user, if it doesn't already exist
    public void createFriendRequestFile(String userId) {
        String filePath = generateFriendRequestFilePath(userId);
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();  // Create the file
                if (created) {

                }
            } else {

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    public void addFriendRequest(String userId, UserAccount friend) {
        
        ArrayList<UserAccount> friendRequests = loadFriendRequests(userId);
        UserAccount c=null;
        for(UserAccount user:friendRequests)
        {
            if(user.getUser().getUserId().equals(friend.getUser().getUserId()))
                c=user;
        }
        if (c==null) {
            friendRequests.add(friend);
            saveFriendRequests(userId, friendRequests);
        }
    }

    // Save friend requests to the JSON file
    public void saveFriendRequests(String userId, ArrayList<UserAccount> friendRequests) {
        String filePath = generateFriendRequestFilePath(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if(friendRequests.isEmpty()) {
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(UserAccount user :friendRequests) {
                String profileJson = objectMapper.writeValueAsString(user);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load friend requests from the JSON file
    public ArrayList<UserAccount> loadFriendRequests(String userId) {

        String filePath = generateFriendRequestFilePath(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
         objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        ArrayList<UserAccount> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
            return accounts;
        }

    }
    public void confirmFriendRequest(UserAccount rec, String friendName) {
    // Load the friend requests and friend lists for both the receiver and sender
    FriendListFileManager fileManager = new FriendListFileManager();

    // Load the receiver's friend requests and the sender's friend list
    ArrayList<UserAccount> friendRequests = loadFriendRequests(rec.getUser().getUserId());
    ArrayList<UserAccount> friends = fileManager.loadFriendList(rec.getUser().getUserId());  // receiver's friend list

    // Load the sender's friend list
    String senderId = null;
    UserAccount friendAccount = null;
    for (UserAccount user : friendRequests) {
        if (user.getUser().getUserName().equalsIgnoreCase(friendName)) { // Case-insensitive comparison
            friendAccount = user;
            senderId = user.getUser().getUserId();  // Get sender's ID
            break;
        }
    }

    // If the friend request is valid (found in the list)
    if (friendAccount != null && senderId != null) {
        // Add the friend (receiver) to the sender's friend list
        ArrayList<UserAccount> senderFriends = fileManager.loadFriendList(senderId);
        senderFriends.add(rec);  // Add the receiver to the sender’s list

        // Add the sender to the receiver's friend list
        friends.add(friendAccount);

        // Remove the friend request from the receiver's list
        friendRequests.remove(friendAccount);

        // Save both the receiver's and sender's updated friend lists
        this.saveFriendRequests(rec.getUser().getUserId(), friendRequests);  // Save the updated friend requests for the receiver
        fileManager.saveFriendList(rec.getUser().getUserId(), friends);  // Save the updated friend list for the receiver

        fileManager.saveFriendList(senderId, senderFriends);  // Save the updated friend list for the sender
    }
}
    public void deleteFriendRequest(String userId, UserAccount user1) {
        // Load friend requests

        ArrayList<UserAccount> friendRequests = loadFriendRequests(userId);

        // Check if the friend is in the friend requests list
        UserAccount friendAccount =null;
        for (UserAccount user : friendRequests) {
            if (user.getUser().getUserId().equalsIgnoreCase(user1.getUser().getUserId())) { // Case-insensitive comparison
                friendAccount=user;
                break;
            }
        }
        if(friendAccount!=null)
        {
            friendRequests.remove(friendAccount);
            this.saveFriendRequests(userId,friendRequests);
        }
    }
}

