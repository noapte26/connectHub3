package FriendMangement.BackEnd;

import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockingListFileManager {
    private final String baseDirectory;
    private final ObjectMapper objectMapper;

    public BlockingListFileManager() {
        this.baseDirectory = "blocking_lists";
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

    // Generate a file path for a user's blocking list
    private String generateBlockingListFilePath(String userId) {
        return baseDirectory + File.separator + "user_" + userId + "_blocking_list.json";
    }

    // Load the blocking list for a user
    public ArrayList<UserAccount> loadBlockingList(String userId) {
        String filePath = generateBlockingListFilePath(userId);

        try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) { // Check if file exists and is not empty
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
                return objectMapper.readValue(file, listType); // Deserialize JSON into List<String>
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(); // Return an empty list if loading fails
    }

    // Save the blocking list for a user
    public void saveBlockingList(String userId, ArrayList<UserAccount> blockingList) {
        String filePath = generateBlockingListFilePath(userId);

        try {
            objectMapper.writeValue(new File(filePath), blockingList);
            System.out.println("Blocking list saved for user ID: " + userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a user to the blocking list
    public void blockUser(String userId, String username) {
        ArrayList<UserAccount> blockingList = loadBlockingList(userId);

        FriendListFileManager fileManager=new FriendListFileManager();

        ArrayList<UserAccount> friends = fileManager.loadFriendList(userId);

        // Check if the friend is in the friend list
        UserAccount friendAccount =null;
        for (UserAccount user : friends) {
            if (user.getUser().getUserName().equalsIgnoreCase(username)) { // Case-insensitive comparison
                friendAccount=user;
                break;
            }
        }
        if(friendAccount!=null)
        {
            friends.remove(friendAccount);
            blockingList.add(friendAccount);
            this.saveBlockingList(userId,blockingList);
            fileManager.saveFriendList(userId,friends);
        }
    }

    // Remove a user from the blocking list
    public void unblockUser(String userId, String username) {
        ArrayList<UserAccount> blockingList = loadBlockingList(userId);

        // Check if the user is in the block list
        UserAccount Account =null;
        for (UserAccount user : blockingList) {
            if (user.getUser().getUserName().equalsIgnoreCase(username)) { // Case-insensitive comparison
                Account=user;
                break;
            }
        }
        if(Account!=null)
        {
            blockingList.remove(Account);
            this.saveBlockingList(userId,blockingList);
        }

    }
}
