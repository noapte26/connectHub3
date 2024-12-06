package FriendMangement.BackEnd;

import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

        if (!friendRequests.contains(friend)) { // Check if the request already exists
            friendRequests.add(friend);
            saveFriendRequests(userId, friendRequests);
        }
    }

    // Save friend requests to the JSON file
    public void saveFriendRequests(String userId, ArrayList<UserAccount> friendRequests) {
        String filePath = generateFriendRequestFilePath(userId);
        try {
            objectMapper.writeValue(new File(filePath), friendRequests);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // Load friend requests from the JSON file
    public ArrayList<UserAccount> loadFriendRequests(String userId) {
        String filePath = generateFriendRequestFilePath(userId);
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return objectMapper.readValue(file, ArrayList.class);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return new ArrayList<>();  // Return an empty list if loading fails
    }
    public void confirmFriendRequest(String userId, String friendName) {
        // Load friend requests and friends list
        FriendListFileManager fileManager=new FriendListFileManager();

        ArrayList<UserAccount> friendRequests = loadFriendRequests(userId);
        ArrayList<UserAccount> friends = fileManager.loadFriendList(userId);

        // Check if the friend is in the friend requests list
        UserAccount friendAccount =null;
        for (UserAccount user : friendRequests) {
            if (user.getUser().getUserName().equalsIgnoreCase(friendName)) { // Case-insensitive comparison
                friendAccount=user;
                break;
            }
        }
        if(friendAccount!=null)
        {
            friends.add(friendAccount);
            friendRequests.remove(friendAccount);
            this.saveFriendRequests(userId,friendRequests);
            fileManager.saveFriendList(userId,friends);
        }
    }
    public void deleteFriendRequest(String userId, String friendName) {
        // Load friend requests

        ArrayList<UserAccount> friendRequests = loadFriendRequests(userId);

        // Check if the friend is in the friend requests list
        UserAccount friendAccount =null;
        for (UserAccount user : friendRequests) {
            if (user.getUser().getUserName().equalsIgnoreCase(friendName)) { // Case-insensitive comparison
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

