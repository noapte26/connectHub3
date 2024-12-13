package groupDataBase;

import Account.UserAccount;
import GroupManagementBackEnd.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;

public class UserGroupsFileManager {
    private  String baseDirectory;
    private ObjectMapper objectMapper;
    private String type;
    public UserGroupsFileManager()
    {
        this.type=type;
        this.baseDirectory ="group_lists";
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
        return baseDirectory + File.separator + "user_" + userId + "_group_list.json";
    }
    public void saveGroups(String userId, ArrayList<Group> groups) {
        String filePath = generateFriendListFilePath(userId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            if(groups.isEmpty())
            {
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            for (Group group : groups)
            {
                String profileJson = objectMapper.writeValueAsString(group);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // Load the friend list for a specific user
    public ArrayList<Group> loadGroups(String userId) {

        String filePath = generateFriendListFilePath(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        ArrayList<Group> groups = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Group g = objectMapper.readValue(line, Group.class);
                groups.add(g);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }
        finally {
            return groups;
        }

    }
}
