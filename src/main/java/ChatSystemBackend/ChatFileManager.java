/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatSystemBackend;

/**
 *
 * @author gg
 */
import Account.UserAccount;
import ContentCreation.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;

public class ChatFileManager {
    private  String baseDirectory;
    private ObjectMapper objectMapper;
    private String type;
    public ChatFileManager(String baseDirectory, String type)
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
    public String generateFriendListFilePath(String chatId) {
        return baseDirectory + File.separator + "Chat_" + chatId + "_"+this.type+".json";
    }
    public void saveChats(String chatId, ArrayList<Chat> Chats) {
        String filePath = generateFriendListFilePath(chatId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
           
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            
            for (Chat chat : Chats)
            {
                String profileJson = objectMapper.writeValueAsString(chat);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // Load the Chat list for a specific chat
    public ArrayList<Chat> loadChats(String chatId) {

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