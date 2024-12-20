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
    public MessageFileManager()
    {
        this.type="message";
        this.baseDirectory = "Message_List";
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
        return baseDirectory + File.separator + "message_" + senderId + "_"+this.type+".json";
    }
    
    public void saveMessage(String chatId, ArrayList<Message> Messages) {
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
 
    public ArrayList<Message> loadMessage(String chatId) {

        String filePath = generateFriendListFilePath(chatId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        ArrayList<Message> messages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Message Message = objectMapper.readValue(line, Message.class);
                messages.add(Message);
            }
        } catch (IOException e) {
            System.err.println("Error reading messages from file: " + e.getMessage());
            throw new RuntimeException("Failed to load messages", e);
        }
        finally {
            return messages;
        }

    }
}