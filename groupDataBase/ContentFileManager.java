package groupDataBase;

import Account.UserAccount;
import ContentCreation.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;

public class ContentFileManager {
    private  String baseDirectory;
    private ObjectMapper objectMapper;
    private String type;
    public ContentFileManager(String baseDirectory, String type)
    {
        this.type=type;
        this.baseDirectory = baseDirectory;
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
    public String generateFriendListFilePath(String groupId) {
        return baseDirectory + File.separator + "group_" + groupId + "_"+this.type+".json";
    }
    public void savePosts(String groupId, ArrayList<Post> posts) {
        String filePath = generateFriendListFilePath(groupId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            if(posts.isEmpty())
            {
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            for (Post post : posts)
            {
                String profileJson = objectMapper.writeValueAsString(post);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // Load the friend list for a specific user
    public ArrayList<Post> loadPosts(String groupId) {

        String filePath = generateFriendListFilePath(groupId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        ArrayList<Post> posts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Post post = objectMapper.readValue(line, Post.class);
                posts.add(post);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }
        finally {
            return posts;
        }

    }

}
