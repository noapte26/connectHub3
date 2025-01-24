package FriendMangement.BackEnd;
import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class FriendListFileManager {
    private  String baseDirectory;
    private  ObjectMapper objectMapper;

    public FriendListFileManager() {
        this.baseDirectory = "friend_lists";
        this.objectMapper = new ObjectMapper();
        createBaseDirectory(); // Ensure the base directory exists
    }

    // Create the base directory if it doesn't exist
    private void createBaseDirectory() {
        File dir = new File(baseDirectory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory and any necessary parent directories
        }
    }

    // Generate a JSON file path for a user's friend list using their user ID
    public String generateFriendListFilePath(String userId) {
        return baseDirectory + File.separator + "user_" + userId + "_friends.json";
    }
    // Create a JSON file for storing friend requests for a user, if it doesn't already exist
    public void createFriendListFile(String userId) {
        String filePath = generateFriendListFilePath(userId);
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();  // Create the file
                if (created) {
                    System.out.println("Friend List file created for user ID: " + userId);
                }
            } else {
                System.out.println("Friend List file already exists for user ID: " + userId);
            }
        } catch (IOException e) {
            System.err.println("Error creating friend List file for user ID: " + userId);
            e.printStackTrace();
        }
    }
    // Save a friend's list for a specific user
    public void saveFriendList(String userId, ArrayList<UserAccount> friends) {
        String filePath = generateFriendListFilePath(userId);
         
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            if(friends.isEmpty())
            {
               // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            for (UserAccount user : friends)
            {
                String profileJson = objectMapper.writeValueAsString(user);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
    }

    // Load the friend list for a specific user
    public ArrayList<UserAccount> loadFriendList(String userId) {
        
        String filePath = generateFriendListFilePath(userId);
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
    

    // Add a friend to the user's friend list
    public void addFriend(String userId, UserAccount friendName) {
        ArrayList<UserAccount> friends = loadFriendList(userId);
        UserAccount friendAccount =null;
        for (UserAccount user : friends) {
            if (user.getUser().getUserId().equals(friendName.getUser().getUserId())) { // Case-insensitive comparison
                friendAccount=user;
            }
        }
        if(friendAccount==null)
        {
            friends.add(friendAccount);
            saveFriendList(userId, friends);
        }
    }

    // Remove a friend from the user's friend list
    public void removeFriend(String userId, UserAccount user1) {
        ArrayList<UserAccount> friends = loadFriendList(userId);
        UserAccount friendAccount =null;
        for (UserAccount user : friends) {
            if (user.getUser().getUserId().equals(user1.getUser().getUserId())) { // Case-insensitive comparison
                friendAccount=user;
                break;
            }
        }
        friends.remove(friendAccount);
        saveFriendList(userId, friends);
    }
    public void BlockFriend(UserAccount whoisblocking,  UserAccount theblocked) {
        BlockingListFileManager fileManager=new BlockingListFileManager();
        fileManager.blockUser(whoisblocking,theblocked);
    }

}

