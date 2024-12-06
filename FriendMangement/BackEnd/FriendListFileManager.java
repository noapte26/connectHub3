package FriendMangement.BackEnd;
import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
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

        try {
            objectMapper.writeValue(new File(filePath), friends);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the friend list for a specific user
    public ArrayList<UserAccount> loadFriendList(String userId) {
        String filePath = generateFriendListFilePath(userId);

        try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) { // Check if the file exists and is not empty
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
                return objectMapper.readValue(file, listType); // Deserialize JSON into List<String>
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(); // Return an empty list if loading fails
    }

    // Add a friend to the user's friend list
    public void addFriend(String userId, String friendName) {
        ArrayList<UserAccount> friends = loadFriendList(userId);
        UserAccount friendAccount =null;
        for (UserAccount user : friends) {
            if (user.getUser().getUserName().equalsIgnoreCase(friendName)) { // Case-insensitive comparison
                friendAccount=user;
                break;
            }
        }
        friends.add(friendAccount);
        saveFriendList(userId, friends);
    }

    // Remove a friend from the user's friend list
    public void removeFriend(String userId, String friendName) {
        ArrayList<UserAccount> friends = loadFriendList(userId);
        UserAccount friendAccount =null;
        for (UserAccount user : friends) {
            if (user.getUser().getUserName().equalsIgnoreCase(friendName)) { // Case-insensitive comparison
                friendAccount=user;
                break;
            }
        }
        friends.remove(friendAccount);
        saveFriendList(userId, friends);
    }
    public void BlockFriend(String userId, String friendName) {
        BlockingListFileManager fileManager=new BlockingListFileManager();
        fileManager.blockUser(userId,friendName);
    }

}

