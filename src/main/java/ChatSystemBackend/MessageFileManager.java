/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatSystemBackend;


import Account.UserAccount;
import ContentCreation.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;

public class MessageFileManager {
    private  String baseDirectory;
    private ObjectMapper objectMapper;
    private String type;
    public MessageFileManager(String baseDirectory, String type)
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
    public String generateFriendListFilePath(String senderId) {
        return baseDirectory + File.separator + "group_" + senderId + "_"+this.type+".json";
    }
    public void savePosts(String senderId, ArrayList<Message> Messages) {
        String filePath = generateFriendListFilePath(senderId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
           
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            
            for (Message post : Messages)
            {
                String profileJson = objectMapper.writeValueAsString(post);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // Load the Messages for a specific chat
    public ArrayList<Chat> loadMessages(String chatId) {

        String filePath = generateFriendListFilePath(chatId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        ArrayList<Chat> chats = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Chat chat = objectMapper.readValue(line, Chat.class);
                chats.add(chat);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }
        finally {
            return chats;
        }

    }

}