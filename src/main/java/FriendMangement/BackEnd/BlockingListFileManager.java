package FriendMangement.BackEnd;

import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;

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
  // Return an empty list if loading fails
    }

    // Save the blocking list for a user
    public void saveBlockingList(String userId, ArrayList<UserAccount> blockingList) {
        String filePath = generateBlockingListFilePath(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
 try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
     if(blockingList.isEmpty())
     {
         // objectMapper.writeValue(file,friends);
         try (PrintWriter pw = new PrintWriter(file)) {}
         catch (IOException e) {
             e.printStackTrace();
         }

     }
            for (UserAccount user : blockingList)
            {String profileJson = objectMapper.writeValueAsString(user);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }}
 catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a user to the blocking list
    public void blockUser(UserAccount whoisblocking, UserAccount theblocked) {
        ArrayList<UserAccount> blockingList = loadBlockingList(whoisblocking.getUser().getUserId());

        FriendListFileManager fileManager = new FriendListFileManager();

        ArrayList<UserAccount> friends = fileManager.loadFriendList(whoisblocking.getUser().getUserId());
        ArrayList<UserAccount> friendsoftheblocked = fileManager.loadFriendList(theblocked.getUser().getUserId());
        // Check if the friend is in the friend list
        UserAccount friendAccount = null;
        for (UserAccount user : friends) {
            if (user.getUser().getUserId().equalsIgnoreCase(theblocked.getUser().getUserId())) { // Case-insensitive comparison
                friendAccount = user;
                break;
            }
        }
        if (friendAccount != null) {
            friends.remove(friendAccount);
            blockingList.add(friendAccount);
            this.saveBlockingList(whoisblocking.getUser().getUserId(), blockingList);
            fileManager.saveFriendList(whoisblocking.getUser().getUserId(), friends);
        }

         friendAccount = null;
        for (UserAccount user : friendsoftheblocked) {
            if (user.getUser().getUserId().equalsIgnoreCase(whoisblocking.getUser().getUserId())) { // Case-insensitive comparison
                friendAccount = user;
                break;
            }
        }

        if (friendAccount != null) {
            friendsoftheblocked.remove(friendAccount);
            fileManager.saveFriendList(theblocked.getUser().getUserId(),friendsoftheblocked);
            FriendSuggestionFileManager file=new FriendSuggestionFileManager();
            file.removeFriendSuggestion(theblocked.getUser().getUserId(),whoisblocking);
        }
        for(UserAccount user:friendsoftheblocked)
            System.out.println(user.getUser().getUserName());

    }

    // Remove a user from the blocking list
    public void unblockUser(String userId, UserAccount user1) {
        ArrayList<UserAccount> blockingList = loadBlockingList(userId);

        // Check if the user is in the block list
        UserAccount Account = null;
        for (UserAccount user : blockingList) {
            if (user.getUser().getUserId().equalsIgnoreCase(user1.getUser().getUserId())) { // Case-insensitive comparison
                Account = user;
                break;
            }
        }
        if (Account != null) {
            blockingList.remove(Account);
            this.saveBlockingList(userId, blockingList);
        }

    }
}
